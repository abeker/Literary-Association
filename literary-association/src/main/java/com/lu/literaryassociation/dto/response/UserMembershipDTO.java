package com.lu.literaryassociation.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserMembershipDTO {

    private String id;
    private String paymentDate;
    private MembershipDTO membership;

}
