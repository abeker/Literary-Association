package com.lu.literaryassociation.services.definition;

import com.lu.literaryassociation.dto.response.UserMembershipDTO;
import com.lu.literaryassociation.dto.response.UserMembershipsDTO;

import java.util.List;
import java.util.UUID;

public interface IUserMembershipService {

    UserMembershipsDTO getMembershipsForPeriod(String token, int forPeriodInDays);

    UserMembershipsDTO payUserMembership(String token, String membershipIdString);

    boolean isUserPaidMembership(UUID userId, int membershipDuration);
}
