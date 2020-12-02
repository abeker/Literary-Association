package com.lu.literaryassociation.service.implementation;

import com.lu.literaryassociation.dto.request.LoginRequest;
import com.lu.literaryassociation.dto.response.LoginResponse;
import com.lu.literaryassociation.entity.LiteraryUser;
import com.lu.literaryassociation.repository.ILiteraryUserRepository;
import com.lu.literaryassociation.service.IAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService implements IAuthService {

    private final ILiteraryUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthService(ILiteraryUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public LoginResponse login(LoginRequest request) throws GeneralException {
        LiteraryUser literaryUser = userRepository.findOneByUsername(request.getUsername());
        //System.out.println(literaryUser.getPassword()+" "+request.getPassword());

        if (literaryUser == null) {
            throw new GeneralException("Wrong username. Try again.", HttpStatus.BAD_REQUEST);
        }

        if (!passwordEncoder.matches(request.getPassword(),literaryUser.getPassword())) {
            throw new GeneralException("Wrong username or password. Try again", HttpStatus.BAD_REQUEST);
        }

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setLiteraryUser(literaryUser);

        return loginResponse;
    }



}
