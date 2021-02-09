package com.lu.literaryassociation.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenreResponse {

    private String id;
    private String code;
    private String name;

}
