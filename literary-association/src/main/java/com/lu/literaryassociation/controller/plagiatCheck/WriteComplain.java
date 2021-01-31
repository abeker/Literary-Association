package com.lu.literaryassociation.controller.plagiatCheck;

import com.lu.literaryassociation.dto.request.FormSubmissionDto;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class WriteComplain {

    @Autowired
    private TaskService taskService;
    @Autowired
    private FormService formService;

    @PostMapping(path = "/writeComplain/{taskId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> writeComplain(
            @RequestBody List<FormSubmissionDto> dto,
            @PathVariable String taskId
            ) {
        System.out.println("task id" + taskId );
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        HashMap<String, Object> map = this.mapListToDto(dto);
        formService.submitTaskForm(taskId, map);
        return ResponseEntity.ok("successfully submitted");
    }

    private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (FormSubmissionDto temp : list) {
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }
}

