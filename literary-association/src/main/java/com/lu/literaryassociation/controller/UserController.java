package com.lu.literaryassociation.controller;

import com.lu.literaryassociation.dto.response.UserStored;
import com.lu.literaryassociation.entity.CommitteeMember;
import com.lu.literaryassociation.services.definition.ICommiteeService;
import com.lu.literaryassociation.services.definition.IUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService _userService;

    private final ICommiteeService iCommiteeService;

    public UserController(IUserService userService, ICommiteeService iCommiteeService) {
        _userService = userService;
        this.iCommiteeService = iCommiteeService;
    }

    @GetMapping(value = "/{username}")
    public UserStored getUser(@PathVariable("username") String username) {
        return _userService.getUser(username);
    }

    @GetMapping(value = "/comm")
    public List<CommitteeMember> getCommitee() {
        return iCommiteeService.getAll();
    }


}
