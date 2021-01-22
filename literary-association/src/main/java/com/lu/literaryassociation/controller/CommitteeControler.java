package com.lu.literaryassociation.controller;

import com.lu.literaryassociation.dto.request.FormSubmissionDto;
import com.lu.literaryassociation.entity.CommitteeMember;
import com.lu.literaryassociation.entity.CommitteeVote;
import com.lu.literaryassociation.services.definition.ICommiteeService;
import com.lu.literaryassociation.services.definition.IVoteService;
import com.lu.literaryassociation.util.enums.WriterStatus;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/committee")
public class CommitteeControler {

    private final TaskService taskService;

    private final RuntimeService runtimeService;

    private final FormService formService;

    private final ICommiteeService iCommiteeService;

    private final IVoteService iVoteService;

    public CommitteeControler(TaskService taskService, RuntimeService runtimeService, FormService formService, ICommiteeService iCommiteeService, IVoteService iVoteService) {
        this.taskService = taskService;
        this.runtimeService = runtimeService;
        this.formService = formService;
        this.iCommiteeService = iCommiteeService;
        this.iVoteService = iVoteService;
    }


    @PostMapping("/vote/{taskId}/{processId}/{username}")
    public @ResponseBody
    ResponseEntity post(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId,
                        @PathVariable String processId, @PathVariable String username) {
        System.out.println("USLA U SUBMIT VOTE");
        System.out.println(processId);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        System.out.println(processInstanceId);

        HashMap<String, Object> map = this.mapListToDto(dto);

        CommitteeVote committeeVote = new CommitteeVote();
        CommitteeMember committeeMember = iCommiteeService.getCommitteeByUserName(username);
        committeeVote.setCommitteeMember(committeeMember);
        committeeVote.setProcessInstanceId(processId);

        for(FormSubmissionDto d: dto){
            System.out.println(d.getFieldId() + " : " + d.getFieldValue());
            if(d.getFieldId().equals("vote")){
                 if(d.getFieldValue().equals("DENIED")){
                     committeeVote.setWriterStatus(WriterStatus.DENIED);
                 }else if(d.getFieldValue().equals("APPROVED")){
                     committeeVote.setWriterStatus(WriterStatus.APPROVED);
                 }else {
                     committeeVote.setWriterStatus(WriterStatus.MORE_MATERIAL);
                 }
            }else{
                committeeVote.setComment(d.getFieldValue());
            }
        }

        int numberOfCommittess = iCommiteeService.getAll().size();
        committeeVote.setCounter(iVoteService.getCounterForNewVote(numberOfCommittess, processInstanceId));

        committeeVote = iVoteService.saveVote(committeeVote);
        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormSubmissionDto temp : list){
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }
}
