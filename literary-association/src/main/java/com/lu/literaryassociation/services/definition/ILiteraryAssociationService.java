package com.lu.literaryassociation.services.definition;

import com.lu.literaryassociation.dto.request.LiteraryAssociationRequest;
import com.lu.literaryassociation.dto.request.ReaderPaymentRequestDTO;
import com.lu.literaryassociation.dto.response.LiteraryAssociationResponse;
import com.lu.literaryassociation.dto.response.ReaderPaymentRequestResponse;
import com.lu.literaryassociation.entity.Authority;
import com.lu.literaryassociation.entity.User;

import java.util.UUID;

public interface ILiteraryAssociationService {

    LiteraryAssociationResponse createLA(LiteraryAssociationRequest request);

    ReaderPaymentRequestResponse createReaderPaymentRequest(ReaderPaymentRequestDTO request);

    void addRole(User user, String role_name);

    void changeReaderPaymentStatus(UUID readerPaymentId, String status);
}
