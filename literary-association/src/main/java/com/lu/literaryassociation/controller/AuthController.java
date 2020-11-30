package com.lu.literaryassociation.controller;

import com.lu.literaryassociation.dto.request.LoginRequest;
import com.lu.literaryassociation.dto.response.LoginResponse;
import com.lu.literaryassociation.service.IAuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("")
    public LoginResponse login(@RequestBody LoginRequest request) throws Exception{
        return authService.login(request);
    }

}
