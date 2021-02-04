package com.lu.literaryassociation.controller;

import com.lu.literaryassociation.dto.response.UserMembershipDTO;
import com.lu.literaryassociation.dto.response.UserMembershipsDTO;
import com.lu.literaryassociation.services.definition.IUserMembershipService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user-memberships")
public class UserMembershipController {

    private final IUserMembershipService _userMembershipService;

    public UserMembershipController(IUserMembershipService userMembershipService) {
        _userMembershipService = userMembershipService;
    }

    @GetMapping(path = "/{forPeriodInDays}")
    @PreAuthorize("hasAuthority('PURCHASE_MEMBERSHIP')")
    public UserMembershipsDTO getMembershipsForPeriod(@RequestHeader("Auth-Token") String token, @PathVariable("forPeriodInDays") int forPeriodInDays) {
        return _userMembershipService.getMembershipsForPeriod(token, forPeriodInDays);
    }

    @PutMapping(path = "/pay/{membershipId}")
    @PreAuthorize("hasAuthority('PURCHASE_MEMBERSHIP')")
    public UserMembershipsDTO payUserMembership(@RequestHeader("Auth-Token") String token, @PathVariable("membershipId") String membershipId) {
        return _userMembershipService.payUserMembership(token, membershipId);
    }

}
