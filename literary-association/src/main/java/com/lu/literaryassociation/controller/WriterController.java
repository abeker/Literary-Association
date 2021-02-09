package com.lu.literaryassociation.controller;

import com.lu.literaryassociation.dto.request.BookCreate;
import com.lu.literaryassociation.dto.request.FormFieldsDto;
import com.lu.literaryassociation.dto.request.FormSubmissionDto;
import com.lu.literaryassociation.dto.response.PublishPaperForm;
import com.lu.literaryassociation.dto.response.WriterBookCreate;
import com.lu.literaryassociation.services.definition.IWriterService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<?> startProcessPublishingBook(@RequestHeader("Auth-Token") String token){
        Map<String, Object> variableMap = _writerService.createMapFromToken(token);
        ProcessInstance pi = _runtimeService.startProcessInstanceByKey("Publish_book_process", variableMap);
        PublishPaperForm publishPaperForm = _writerService.getProccessIdAndFormFields(pi);
        return ResponseEntity.ok(publishPaperForm);
    }

    @GetMapping(path = "/get-form/{processInstanceId}", produces = "application/json")
    public FormFieldsDto getPublishPaperFormFields(@PathVariable("processInstanceId") String processInstanceId) {
        return _writerService.getFormFieldsForPublishPaper(processInstanceId);
    }

    @PostMapping(path = "/submit-form/{processInstanceId}", produces = "application/json")
    public void submitPublishForm(@RequestBody List<FormSubmissionDto> submitedFields,
                                  @PathVariable("processInstanceId") String processInstanceId) {
        _writerService.submitPublishForm(submitedFields, processInstanceId);
    }

    @PostMapping(path = "/create-book", produces = "application/json")
    public WriterBookCreate createBook(@RequestHeader("Auth-Token") String token,
                                       @RequestBody BookCreate bookCreateBody) {
        return _writerService.createBook(token, bookCreateBody);
    }

}
