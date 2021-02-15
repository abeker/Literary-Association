package com.lu.literaryassociation.entity;

import com.lu.literaryassociation.util.enums.PaymentRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReaderPaymentRequest extends BaseEntity {

    private int paymentCounter;
    private String bankCode;

    @Enumerated(value = EnumType.STRING)
    private PaymentRequestStatus status = PaymentRequestStatus.PENDING;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "readerId")
    private Reader reader;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "bookId")
    private Book book;
}
