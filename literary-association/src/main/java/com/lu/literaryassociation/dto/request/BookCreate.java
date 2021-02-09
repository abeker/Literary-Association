package com.lu.literaryassociation.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class BookCreate {

    private String title;
    private String synopsis;
    private List<String> genreIds;
    private int numberOfPages;
    private int price;

}
