package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.dto.request.ReaderRegistration;
import com.lu.literaryassociation.dto.response.CreatedReader;
import com.lu.literaryassociation.entity.Reader;
import com.lu.literaryassociation.repository.IReaderRepository;
import com.lu.literaryassociation.services.definition.IReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReaderService implements IReaderService {

    @Autowired
    IReaderRepository iReaderRepository;

    @Override
    public CreatedReader registration(ReaderRegistration request) {

        return CreatedReader.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername()).build();
    }

    public Reader findReaderByUsername(String username){
        return iReaderRepository.findOneByUsername(username);
    }

}
