package com.lu.literaryassociation.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MembershipDTO {

    private String id;
    private double amount;
    private String dateOpened;
    private String dateClosed;

}
