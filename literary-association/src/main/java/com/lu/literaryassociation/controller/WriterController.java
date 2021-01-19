package com.lu.literaryassociation.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/writers")
public class WriterController {

    private final RuntimeService runtimeService;

    public WriterController(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @GetMapping(path = "/publish-start", produces = "application/json")
    public ResponseEntity<String> startProcessPublishingBook(){
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("Publish_book_process");
        return ResponseEntity.ok(pi.getId());
    }

}
