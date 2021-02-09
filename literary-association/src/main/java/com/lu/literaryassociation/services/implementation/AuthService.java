package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.dto.request.LoginRequest;
import com.lu.literaryassociation.dto.response.UserResponse;
import com.lu.literaryassociation.entity.LoginAttempts;
import com.lu.literaryassociation.entity.User;
import com.lu.literaryassociation.entity.UserDetailsImpl;
import com.lu.literaryassociation.repository.ILoginAttemptsRepository;
import com.lu.literaryassociation.repository.IUserRepository;
import com.lu.literaryassociation.security.SecurityEscape;
import com.lu.literaryassociation.security.TokenUtils;
import com.lu.literaryassociation.services.definition.IAuthService;
import com.lu.literaryassociation.util.exceptions.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
public class AuthService implements IAuthService {

    private final AuthenticationManager _authenticationManager;
    private final TokenUtils _tokenUtils;
    private final PasswordEncoder _passwordEncoder;
    private final IUserRepository _userRepository;
    private final ILoginAttemptsRepository _loginAttemptsRepository;

    public AuthService(AuthenticationManager authenticationManager, TokenUtils tokenUtils, PasswordEncoder passwordEncoder, IUserRepository userRepository, ILoginAttemptsRepository loginAttemptsRepository) {
        _authenticationManager = authenticationManager;
        _tokenUtils = tokenUtils;
        _passwordEncoder = passwordEncoder;
        _userRepository = userRepository;
        _loginAttemptsRepository = loginAttemptsRepository;
    }

    @Override
    public UserResponse login(LoginRequest loginRequest, HttpServletRequest httpServletRequest, String luName) {
        sanitizeInputValues(loginRequest);
        User user = _userRepository.findOneByUsername(loginRequest.getUsername());
        LoginAttempts loginAttempt = _loginAttemptsRepository.findOneByIpAddress(httpServletRequest.getRemoteAddr());

        if(isUserLoginBlocked(loginAttempt)) {
            throw new GeneralException("You have reached your logging limit, please try again later.", HttpStatus.CONFLICT);
        }

        if(!isUserFound(user, loginRequest)) {
            changeLoginAttempts(loginAttempt, httpServletRequest);
            throw new GeneralException("Bad credentials.", HttpStatus.NOT_FOUND);
        }

        if(!user.getLiteraryAssociation().getName().toLowerCase().equals(luName.toLowerCase())) {
            throw new GeneralException("Incorrect Literary Association.", HttpStatus.NOT_EXTENDED);
        }

        checkUserStatus(user);
        Authentication authentication = loginSimpleUser(loginRequest.getUsername(), loginRequest.getPassword());
        refreshUserActivityTime(user);
        return createLoginUserResponse(authentication, user);
    }

    private void sanitizeInputValues(LoginRequest request) {
        request.setUsername(SecurityEscape.cleanIt(request.getUsername()));
        request.setPassword(SecurityEscape.cleanIt(request.getPassword()));
    }

    private void refreshUserActivityTime(User user) {
        user.setLastTimeActive(LocalDateTime.now());
        _userRepository.save(user);
    }

    private void changeLoginAttempts(LoginAttempts loginAttempt, HttpServletRequest httpServletRequest) {
        if(loginAttempt == null){
            LoginAttempts newLoginAttempt = new LoginAttempts();
            newLoginAttempt.setIpAddress(httpServletRequest.getRemoteAddr());
            newLoginAttempt.setFirstMistakeDateTime(LocalDateTime.now());
            newLoginAttempt.setAttempts(1);
            _loginAttemptsRepository.save(newLoginAttempt);
            return;
        } else if(loginAttempt.getFirstMistakeDateTime().plusHours(12L).isBefore(LocalDateTime.now())){
            loginAttempt.setFirstMistakeDateTime(LocalDateTime.now());
            loginAttempt.setAttempts(0);
        }
        loginAttempt.setAttempts(loginAttempt.getAttempts() + 1);
        _loginAttemptsRepository.save(loginAttempt);
    }

    private boolean isUserLoginBlocked(LoginAttempts loginAttempt) {
        return loginAttempt != null && loginAttempt.getAttempts() > 5 && loginAttempt.getFirstMistakeDateTime().plusHours(12L).isAfter(LocalDateTime.now());
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

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        return authentication;
    }

    private void checkUserStatus(User userToCheck) {
        if(!userToCheck.isApproved()) {
            throw new GeneralException("Your registration hasn't been approved yet.", HttpStatus.BAD_REQUEST);
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
