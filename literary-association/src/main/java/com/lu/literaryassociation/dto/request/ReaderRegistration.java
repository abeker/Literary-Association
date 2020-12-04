package com.lu.literaryassociation.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
public class ReaderRegistration {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String city;
    private String country;
    private boolean betaReader;

}
