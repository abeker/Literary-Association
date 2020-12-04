package com.lu.literaryassociation.services.definition;

import com.lu.literaryassociation.dto.request.ReaderRegistration;
import com.lu.literaryassociation.dto.response.CreatedReader;

public interface IReaderService {

    CreatedReader registration(ReaderRegistration request);

}
