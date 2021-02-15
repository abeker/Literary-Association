package com.lu.literaryassociation.controller;

import com.lu.literaryassociation.dto.response.DeletedUser;
import com.lu.literaryassociation.services.definition.IAdminService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private IAdminService _adminService;

    public AdminController(IAdminService adminService) {
        _adminService = adminService;
    }

    @GetMapping("/check-inactive-users")
    @PreAuthorize("hasAuthority('DELETE_INACTIVE_USERS')")
    public List<DeletedUser> deleteInactive() {
        return _adminService.deleteInactiveUsers(150);
    }
}
