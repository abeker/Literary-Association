package com.lu.literaryassociation.controller;

import camundajar.impl.com.google.gson.JsonObject;
import camundajar.impl.scala.util.parsing.json.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.lu.literaryassociation.entity.*;
import com.lu.literaryassociation.services.implementation.ConfirmationTokenService;
import com.lu.literaryassociation.services.implementation.ReaderService;
import com.lu.literaryassociation.services.implementation.UserService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/welcome")
public class CamundaController {
    @Autowired
    IdentityService identityService;
    @Autowired
    TaskService taskService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    FormService formService;

    @Autowired
    UserService userService;

    @Autowired
    ReaderService readerService;

    @Autowired
    ConfirmationTokenService confirmationTokenService;

    @GetMapping(path = "/get", produces = "application/json")
    public @ResponseBody
    FormFieldsDto get() {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("Popuni_formu");

        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFieldsDto(task.getId(), pi.getId(), properties);
    }

    @GetMapping(path = "/get/tasks/{processInstanceId}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<TaskDto>> get(@PathVariable String processInstanceId) {

        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        List<TaskDto> dtos = new ArrayList<TaskDto>();
        for (Task task : tasks) {
            TaskDto t = new TaskDto(task.getId(), task.getName(), task.getAssignee());
            dtos.add(t);
        }

        return new ResponseEntity(dtos,  HttpStatus.OK);
    }


    @PostMapping(path = "/post/{taskId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity post(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(dto);
        for(FormSubmissionDto d: dto){
            System.out.println(d.getFieldId() + " : " + d.getFieldValue());
        }

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "registration", dto);
        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping(path = "/tasks/claim/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity claim(@PathVariable String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        String user = (String) runtimeService.getVariable(processInstanceId, "username");
        taskService.claim(taskId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/tasks/complete/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<TaskDto>> complete(@PathVariable String taskId) {
        Task taskTemp = taskService.createTaskQuery().taskId(taskId).singleResult();
        taskService.complete(taskId);
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(taskTemp.getProcessInstanceId()).list();
        List<TaskDto> dtos = new ArrayList<TaskDto>();
        for (Task task : tasks) {
            TaskDto t = new TaskDto(task.getId(), task.getName(), task.getAssignee());
            dtos.add(t);
        }
        return new ResponseEntity<List<TaskDto>>(dtos, HttpStatus.OK);
    }

    @GetMapping("/confirm-account/{instanceId}")
    public ResponseEntity<String> activateAccount(@PathVariable String instanceId, @RequestParam("token")String token){
        System.out.println("TOKEN: " + token);
        ConfirmationToken confirmationToken = confirmationTokenService.findByToken(token);
        if(confirmationToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Your token is invalid");
        }
        //is it expired?
        if(confirmationToken.getExpiryDate().before(new Date())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Your token has expired");
        }
        Reader user = readerService.findReaderByUsername(confirmationToken.getUserEntity().getUsername());

        runtimeService.createMessageCorrelation("Message_142ir1i").processInstanceId(instanceId).correlateAll();
        user.setApproved(true);
        userService.saveUser(user);
        //remove confirmationTOken
        confirmationTokenService.delete(confirmationToken);



        return ResponseEntity.ok("Successfully activated account: " + user.getUsername());
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

// list all running/unsuspended instances of the process
//		    ProcessInstance processInstance =
//		        runtimeService.createProcessInstanceQuery()
//		            .processDefinitionKey("Process_1")
//		            .active() // we only want the unsuspended process instances
//		            .list().get(0);

//			Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);


