package com.lu.literaryassociation.controller;

import com.lu.literaryassociation.dto.request.ReaderRegistration;
import com.lu.literaryassociation.dto.response.CreatedReader;
import com.lu.literaryassociation.services.definition.IReaderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/readers")
public class ReaderController {

    private final IReaderService _readerService;

    public ReaderController(IReaderService readerService) {
        _readerService = readerService;
    }

    @PostMapping("")
    public CreatedReader registration(@RequestBody ReaderRegistration request) {
        return _readerService.registration(request);
    }

}
