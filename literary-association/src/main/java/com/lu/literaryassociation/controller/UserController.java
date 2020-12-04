package com.lu.literaryassociation.controller;

import com.lu.literaryassociation.dto.request.LoginRequest;
import com.lu.literaryassociation.dto.response.UserResponse;
import com.lu.literaryassociation.dto.response.UserStored;
import com.lu.literaryassociation.services.definition.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService _userService;

    public UserController(IUserService userService) {
        _userService = userService;
    }

    @GetMapping(value = "/{username}")
    public UserStored getUser(@PathVariable("username") String username) {
        return _userService.getUser(username);
    }

}
