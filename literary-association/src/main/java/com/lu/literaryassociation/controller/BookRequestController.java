package com.lu.literaryassociation.controller;

import com.lu.literaryassociation.dto.response.BookRequestDTO;
import com.lu.literaryassociation.services.definition.IBookRequestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book-requests")
public class BookRequestController {

    private final IBookRequestService _bookRequestService;

    public BookRequestController(IBookRequestService bookRequestService) {
        _bookRequestService = bookRequestService;
    }

    @GetMapping(produces = "application/json")
    public List<BookRequestDTO> getAll() {
        return _bookRequestService.getAll();
    }

    @GetMapping(path = "/process/{processInstanceId}", produces = "application/json")
    public BookRequestDTO getBookRequestFromProcess(@PathVariable("processInstanceId") String processInstanceId) {
        return _bookRequestService.getBookRequestFromProcess(processInstanceId);
    }

    @GetMapping(path = "/change/{processInstanceId}")
    public boolean canChangeHandwrite(@PathVariable("processInstanceId") String processInstanceId) {
        return _bookRequestService.canChangeHandwrite(processInstanceId);
    }


}
