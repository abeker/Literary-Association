package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.entity.Writer;
import com.lu.literaryassociation.repository.IWriterRepository;
import com.lu.literaryassociation.services.definition.IWriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WriterService implements IWriterService {


    @Autowired
    IWriterRepository iWriterRepository;

    public Writer findWriterByUsername(String username){
        return iWriterRepository.findOneByUsername(username);
    }

}
