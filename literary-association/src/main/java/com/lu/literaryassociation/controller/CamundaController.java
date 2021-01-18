package com.lu.literaryassociation.controller;

import com.lu.literaryassociation.dto.request.FormFieldsDto;
import com.lu.literaryassociation.dto.request.FormSubmissionDto;
import com.lu.literaryassociation.dto.request.TaskDto;
import com.lu.literaryassociation.services.implementation.ConfirmationTokenService;
import com.lu.literaryassociation.services.implementation.GenreService;
import com.lu.literaryassociation.services.implementation.ReaderService;
import com.lu.literaryassociation.services.implementation.UserService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    @Autowired
    GenreService genreService;

    @GetMapping(path = "/startProcess", produces = "application/json")
    public  ResponseEntity<String> startProcess(){
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("Popuni_formu");
        return ResponseEntity.ok(pi.getId());
    }


    @GetMapping(path = "/get/{processInstanceId}", produces = "application/json")
    public @ResponseBody
    FormFieldsDto getFormFields(@PathVariable("processInstanceId") String processInstanceId) {
        System.out.println("PROCES: " + processInstanceId);
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFieldsDto(task.getId(),processInstanceId, properties);
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
    public ResponseEntity<String> activateAccount(@PathVariable String instanceId, @RequestParam("token")String token) throws URISyntaxException {
        System.out.println("TOKEN: " + token);

        ActivityInstance activityInstance = runtimeService.getActivityInstance(instanceId);
        String[] exids = activityInstance.getExecutionIds();
        System.out.println("DUZINA: " + exids.length + "  PRVI: " + exids[0]);
        runtimeService.setVariable(exids[0],"confirmationToken",token);
        runtimeService.createMessageCorrelation("Message_142ir1i").processInstanceId(instanceId).correlateAll();

        URI yahoo = new URI("http://localhost:4200/auth/login");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(yahoo);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
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



