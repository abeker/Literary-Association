package com.lu.literaryassociation.controller.plagiatCheck;

import com.lu.literaryassociation.dto.request.FormSubmissionDto;
import com.lu.literaryassociation.entity.Editor;
import com.lu.literaryassociation.entity.EditorComment;
import com.lu.literaryassociation.repository.IEditorRepository;
import com.lu.literaryassociation.services.implementation.EditorCommentService;
import com.lu.literaryassociation.services.implementation.EditorService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
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
public class EditorWritesNotes {
    @Autowired
    private EditorCommentService editorCommentService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FormService formService;
    @Autowired
    private RuntimeService runtimeService;


    @PostMapping(path = "/editors/notes/{taskId}/{username}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<EditorComment> editorWritesNotes(
            @RequestBody List<FormSubmissionDto> dto,
            @PathVariable String taskId,
            @PathVariable String username
    )
    {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        HashMap<String, Object> map = mapListToDto(dto);
        String comment = (String) map.get("notes");
        String title = (String) runtimeService.getVariable(processInstanceId, "title");
        System.out.println("TITLE :" + title);

        EditorComment editorComment =  editorCommentService.saveEditorComment(comment, username, title);
        formService.submitTaskForm(taskId, map);
        return ResponseEntity.ok(editorComment);
    }

    private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (FormSubmissionDto temp : list) {
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }
}
