package com.lu.literaryassociation.controller;

import com.lu.literaryassociation.dto.request.FormFieldsDto;
import com.lu.literaryassociation.services.definition.IWriterService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/writers")
public class WriterController {

    private final RuntimeService _runtimeService;

    private final IWriterService _writerService;

    public WriterController(RuntimeService runtimeService, IWriterService writerService) {
        _runtimeService = runtimeService;
        _writerService = writerService;
    }

    @GetMapping(path = "/publish-start", produces = "application/json")
    public ResponseEntity<String> startProcessPublishingBook(@RequestHeader("Auth-Token") String token){
        Map<String, Object> variableMap = _writerService.createMapFromToken(token);
        ProcessInstance pi = _runtimeService.startProcessInstanceByKey("Publish_book_process");
        return ResponseEntity.ok(pi.getId());
    }

    @GetMapping(path = "/get-form/{processInstanceId}", produces = "application/json")
    public FormFieldsDto getPublishPaperFormFields(@PathVariable("processInstanceId") String processInstanceId) {
        return _writerService.getFormFieldsForPublishPaper(processInstanceId);
    }


}
