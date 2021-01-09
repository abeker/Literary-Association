package com.lu.literaryassociation.dto.request;

import lombok.Data;

@Data
public class LiteraryAssociationRequest {

    private String name;
    private String country;
    private String city;
    private String streetNumber;
    private String zipCode;
    private double membershipAmount;

}
