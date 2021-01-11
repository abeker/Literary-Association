package com.lu.literaryassociation.dto.request;

import lombok.Data;

@Data
public class ReaderPaymentRequestDTO {

    private int paymentCounter;
    private String bankCode;
    private String readerId;
    private String bookId;

}
