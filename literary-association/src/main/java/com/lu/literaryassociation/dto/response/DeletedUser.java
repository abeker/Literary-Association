package com.lu.literaryassociation.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeletedUser {

    private String id;
    private String username;
    private String role;

}
