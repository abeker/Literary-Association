package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.dto.request.ReaderRegistration;
import com.lu.literaryassociation.dto.response.CreatedReader;
import com.lu.literaryassociation.services.definition.IReaderService;
import org.springframework.stereotype.Service;

@Service
public class ReaderService implements IReaderService {

    @Override
    public CreatedReader registration(ReaderRegistration request) {
        // TODO Povezati sa Camundom i odraditi proces

        return CreatedReader.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername()).build();
    }

}
