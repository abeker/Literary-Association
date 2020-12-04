package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.dto.request.LoginRequest;
import com.lu.literaryassociation.dto.response.UserResponse;
import com.lu.literaryassociation.entity.*;
import com.lu.literaryassociation.repository.IUserRepository;
import com.lu.literaryassociation.security.TokenUtils;
import com.lu.literaryassociation.services.definition.IAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthService implements IAuthService {

    private final AuthenticationManager _authenticationManager;
    private final TokenUtils _tokenUtils;
    private final PasswordEncoder _passwordEncoder;
    private final IUserRepository _userRepository;

    public AuthService(AuthenticationManager authenticationManager, TokenUtils tokenUtils, PasswordEncoder passwordEncoder, IUserRepository userRepository) {
        _authenticationManager = authenticationManager;
        _tokenUtils = tokenUtils;
        _passwordEncoder = passwordEncoder;
        _userRepository = userRepository;
    }

    @Override
    public UserResponse login(LoginRequest request, HttpServletRequest httpServletRequest) {
        User user = _userRepository.findOneByUsername(request.getUsername());
        if(!isUserFound(user, request)) {
            throw new GeneralException("Bad credentials.", HttpStatus.BAD_REQUEST);
        }

        checkUserStatus(user);
        Authentication authentication = loginSimpleUser(request.getUsername(), request.getPassword());
        return createLoginUserResponse(authentication, user);
    }

    private Authentication loginSimpleUser(String mail, String password) {
        Authentication authentication = null;
        try {
            authentication = _authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(mail, password));
        }catch (BadCredentialsException e){
            throw new GeneralException("Bad credentials.", HttpStatus.BAD_REQUEST);
        }catch (DisabledException e){
            throw new GeneralException("Your registration request hasn't been approved yet.", HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            e.printStackTrace();
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    private void checkUserStatus(User userToCheck) {
        switch (userToCheck.getUserType()){
            case EDITOR:
                if(!((Editor)userToCheck).isApproved()) {
                    throw new GeneralException("Your registration hasn't been approved yet.", HttpStatus.BAD_REQUEST);
                }
            break;
            case LECTOR:
                if(!((Lector)userToCheck).isApproved()) {
                    throw new GeneralException("Your registration hasn't been approved yet.", HttpStatus.BAD_REQUEST);
                }
            break;
            case READER:
                if(!((Reader)userToCheck).isApproved()) {
                    throw new GeneralException("Your registration hasn't been approved yet.", HttpStatus.BAD_REQUEST);
                }
            break;
            case WRITER:
                if(!((Writer)userToCheck).isRegistrationApproved()) {
                    throw new GeneralException("Your registration hasn't been approved yet.", HttpStatus.BAD_REQUEST);
                }
            break;
        }
    }

    private boolean isUserFound(User user, LoginRequest request) {
        return user != null && _passwordEncoder.matches(request.getPassword(), user.getPassword());
    }

    private UserResponse createLoginUserResponse(Authentication authentication, User user) {
        UserDetailsImpl userLog = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = _tokenUtils.generateToken(userLog.getUsername());
        int expiresIn = _tokenUtils.getExpiredIn();

        UserResponse userResponse = mapUserToUserResponse(user);
        userResponse.setToken(jwt);
        userResponse.setTokenExpiresIn(expiresIn);

        return userResponse;
    }

    private UserResponse mapUserToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());

        userResponse.setUsername(user.getUsername());
        userResponse.setUserRole(user.getUserType().toString());
        return userResponse;
    }

}
