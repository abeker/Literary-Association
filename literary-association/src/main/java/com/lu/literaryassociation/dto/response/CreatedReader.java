package com.lu.literaryassociation.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreatedReader {

    private String username;
    private String email;
    private String firstName;
    private String lastName;

}
