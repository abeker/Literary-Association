package com.lu.literaryassociation.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookResponse {

    private String id;
    private String ISBN;
    private String publishYear;
    private String publishPlace;
    private int numberOfPages;
    private double price;
    private BookRequestDTO bookRequest;

}
