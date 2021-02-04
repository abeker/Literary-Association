package com.lu.literaryassociation.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserMembershipsDTO {

    private boolean needToPay;
    private List<UserMembershipDTO> userMemberships;

}
