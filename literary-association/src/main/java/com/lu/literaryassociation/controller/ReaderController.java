package com.lu.literaryassociation.controller;

import com.lu.literaryassociation.dto.request.FormSubmissionDto;
import com.lu.literaryassociation.dto.request.ReaderRegistration;
import com.lu.literaryassociation.dto.response.CreatedReader;
import com.lu.literaryassociation.entity.*;
import com.lu.literaryassociation.services.definition.IReaderService;
import com.lu.literaryassociation.services.definition.IUserService;
import com.lu.literaryassociation.util.enums.WriterStatus;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/readers")
public class ReaderController {

    private final IReaderService _readerService;

    private final IUserService _userService;

    private final TaskService taskService;

    private final RuntimeService runtimeService;

    private final FormService formService;

    public ReaderController(IReaderService readerService, IUserService userService, TaskService taskService, RuntimeService runtimeService, FormService formService) {
        _readerService = readerService;
        _userService = userService;
        this.taskService = taskService;
        this.runtimeService = runtimeService;
        this.formService = formService;
    }

    @PostMapping("")
    public CreatedReader registration(@RequestBody ReaderRegistration request) {
        return _readerService.registration(request);
    }

    @GetMapping("/betaReaders/{genreName}")
    public List<String> getBetaReadersByGenre(@PathVariable("genreName") String genreName){
        return  _readerService.findBetaReaderInfoByGenre(genreName);
    }

    @GetMapping("/isBetaReader/{username}")
    public boolean isBetaReader(@PathVariable("username") String username){
        return  _readerService.isBetaReader(username);
    }

    @PostMapping(path = "/post/{taskId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity post(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        System.out.println("Cuvam listu beta readera");
        ArrayList<String> betaReaders = new ArrayList<>();
        HashMap<String, Object> map = this.mapListToDto(dto);
        String choosenBeta = "";
        for(FormSubmissionDto d: dto){
            System.out.println(d.getFieldId() + " : " + d.getFieldValue());
            if(d.getFieldId().equals("choosenBetaReaders"))
                choosenBeta = d.getFieldValue();
        }
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        String[] parts = choosenBeta.split(";");
        for(int i=0; i<parts.length; i++){
            System.out.println(parts[i]);
            betaReaders.add(parts[i]);
        }

        System.out.println(betaReaders);
        runtimeService.setVariable(processInstanceId, "betaReaders", betaReaders);
        runtimeService.setVariable(processInstanceId, "betaReadersSize", betaReaders.size());
        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/comment/{taskId}/{processId}/{username}")
    public @ResponseBody
    ResponseEntity post(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId,
                        @PathVariable String processId, @PathVariable String username) {
        System.out.println("USLA U SUBMIT BETA COMENTARA");
        System.out.println(processId);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        System.out.println(processInstanceId);

        HashMap<String, Object> map = this.mapListToDto(dto);

        BetaReaderComment betaReaderComment = new BetaReaderComment();
        Reader r = _readerService.findReaderByUsername(username);
        betaReaderComment.setBetaReader(r.getBetaReader());
        betaReaderComment.setProcessInstance(processInstanceId);

        for(FormSubmissionDto d: dto){
            System.out.println(d.getFieldId() + " : " + d.getFieldValue());
            if(d.getFieldId().equals("comment")){
                betaReaderComment.setCommentText(d.getFieldValue());
            }
        }

        betaReaderComment = _readerService.saveBetaReaderComment(betaReaderComment);
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
