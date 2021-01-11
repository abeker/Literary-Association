package com.lu.literaryassociation.services.definition;

import com.lu.literaryassociation.dto.request.LiteraryAssociationRequest;
import com.lu.literaryassociation.dto.request.ReaderPaymentRequestDTO;
import com.lu.literaryassociation.dto.response.LiteraryAssociationResponse;
import com.lu.literaryassociation.dto.response.ReaderPaymentRequestResponse;

public interface ILiteraryAssociationService {

    LiteraryAssociationResponse createLA(LiteraryAssociationRequest request);

    ReaderPaymentRequestResponse createReaderPaymentRequest(ReaderPaymentRequestDTO request);
}
