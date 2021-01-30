package com.lu.literaryassociation.controller;

import com.lu.literaryassociation.dto.response.HandwriteDto;
import com.lu.literaryassociation.services.definition.IBookRequestService;
import com.lu.literaryassociation.services.definition.IHandwriteService;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/handwrites")
public class HandwriteController {

    private final RuntimeService _runtimeService;
    private final IHandwriteService _handwriteService;
    private final IBookRequestService _bookRequestService;

    public HandwriteController(RuntimeService runtimeService, IHandwriteService handwriteService, IBookRequestService bookRequestService) {
        _runtimeService = runtimeService;
        _handwriteService = handwriteService;
        _bookRequestService = bookRequestService;
    }

    @GetMapping(path = "/proccess/{processInstanceId}", produces = "application/json")
    public HandwriteDto getPublishPaperFormFields(@PathVariable("processInstanceId") String processInstanceId) {
        return _bookRequestService.getHandwriteFromProccess(processInstanceId);
    }

}
