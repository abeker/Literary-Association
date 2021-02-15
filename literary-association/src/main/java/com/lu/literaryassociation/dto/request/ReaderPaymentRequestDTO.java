package com.lu.literaryassociation.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ReaderPaymentRequestDTO {

    private int paymentCounter;
    private String bankCode;
    private String readerId;
    private List<String> bookIds;

}
