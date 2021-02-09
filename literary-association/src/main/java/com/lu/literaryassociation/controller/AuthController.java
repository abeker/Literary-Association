package com.lu.literaryassociation.controller;

import com.lu.literaryassociation.dto.request.LoginRequest;
import com.lu.literaryassociation.dto.response.UserResponse;
import com.lu.literaryassociation.services.definition.IAuthService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PutMapping("/login/{luName}")
    public UserResponse login(@RequestBody LoginRequest request,
                              @PathVariable("luName") String luName,
                              HttpServletRequest httpServletRequest) throws Exception{
        return authService.login(request, httpServletRequest, luName);
    }

}
