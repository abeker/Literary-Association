package com.lu.literaryassociation.services.definition;

import com.lu.literaryassociation.dto.request.LiteraryAssociationRequest;
import com.lu.literaryassociation.dto.response.LiteraryAssociationResponse;

public interface ILiteraryAssociationService {

    LiteraryAssociationResponse createLA(LiteraryAssociationRequest request);

}
