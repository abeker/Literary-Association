package com.lu.literaryassociation.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LuSecret {

    private String secret;
    private String password;

}
