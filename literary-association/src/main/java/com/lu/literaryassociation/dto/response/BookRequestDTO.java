package com.lu.literaryassociation.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class BookRequestDTO {

    private String id;
    private String title;
    private List<String> genres;
    private List<String> writers;
    private String synopsis;
    private boolean isApproved;

}
