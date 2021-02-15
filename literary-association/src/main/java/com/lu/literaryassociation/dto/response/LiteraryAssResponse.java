package com.lu.literaryassociation.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LiteraryAssResponse {

    private String id;
    private String name;
    private String address;

}
