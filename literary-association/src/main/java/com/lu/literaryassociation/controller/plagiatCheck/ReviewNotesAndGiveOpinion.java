package com.lu.literaryassociation.controller.plagiatCheck;

import com.lu.literaryassociation.dto.request.FormSubmissionDto;
import com.lu.literaryassociation.entity.BoardMemberOpinion;
import com.lu.literaryassociation.entity.EditorComment;
import com.lu.literaryassociation.services.implementation.BoardMemberOpinionService;
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
public class ReviewNotesAndGiveOpinion {

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private BoardMemberOpinionService  boardMemberOpinionService;

    @PostMapping(path = "/giveOpinion/{taskId}/{username}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> reviewNotesAndGiveOpinion(
            @RequestBody List<FormSubmissionDto> dto,
            @PathVariable String taskId,
            @PathVariable String username
    ) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        String title = (String) runtimeService.getVariable(processInstanceId, "title");

        HashMap<String, Object> map = mapListToDto(dto);
        boolean plagiat = Boolean.parseBoolean((String)map.get("plagiat"));

        BoardMemberOpinion boardMemberOpinion = new BoardMemberOpinion();
        boardMemberOpinion.setBoardMember(username);
        boardMemberOpinion.setPlagiat(plagiat);
        boardMemberOpinion.setTitle(title);

        BoardMemberOpinion response = boardMemberOpinionService.save(boardMemberOpinion);
        if(response == null){
            return ResponseEntity.badRequest().body("some error occured");
        }

        formService.submitTaskForm(taskId, map);
        return ResponseEntity.ok(response);
    }

    private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (FormSubmissionDto temp : list) {
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }
}
