package com.lu.literaryassociation.controller;

import com.lu.literaryassociation.dto.request.FormFieldsDto;
import com.lu.literaryassociation.dto.request.FormSubmissionDto;
import com.lu.literaryassociation.services.definition.IEditorService;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/editors")
public class EditorController {

    private final RuntimeService _runtimeService;
    private final IEditorService _editorService;

    public EditorController(RuntimeService runtimeService, IEditorService editorService) {
        _runtimeService = runtimeService;
        _editorService = editorService;
    }

    @GetMapping(path = "/get-review-form/{processInstanceId}", produces = "application/json")
    public FormFieldsDto getPublishPaperFormFields(@PathVariable("processInstanceId") String processInstanceId) {
        return _editorService.getFormFieldsForReview(processInstanceId);
    }

    @PostMapping(path = "/submit-form/{processInstanceId}/{reason}", produces = "application/json")
    public void submitWriterBookRequest(@RequestBody List<FormSubmissionDto> submitedFields,
                                        @PathVariable("processInstanceId") String processInstanceId,
                                        @PathVariable("reason") String reason) {
        _editorService.submitWriterBookRequest(submitedFields, processInstanceId, reason);
    }
    
    @GetMapping(path = "/get-plagiat-form/{processInstanceId}", produces = "application/json")
    public FormFieldsDto getPlagiatForm(@PathVariable("processInstanceId") String processInstanceId) {
        return _editorService.getPlagiatForm(processInstanceId);
    }
}
