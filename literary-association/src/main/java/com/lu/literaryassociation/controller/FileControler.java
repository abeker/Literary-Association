package com.lu.literaryassociation.controller;

import com.lu.literaryassociation.services.definition.IFileService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileControler {

    private final TaskService taskService;

    private final RuntimeService runtimeService;

    private final FormService formService;

    private final IFileService iFileService;

    public FileControler(TaskService taskService, RuntimeService runtimeService, FormService formService, IFileService iFileService) {
        this.taskService = taskService;
        this.runtimeService = runtimeService;
        this.formService = formService;
        this.iFileService = iFileService;
    }

    @PostMapping("/fileUpload/{taskId}")
    public @ResponseBody
    ResponseEntity post(@RequestParam("files") MultipartFile[] files, @PathVariable String taskId) throws Exception {
        System.out.println("USLA U SUBMIT UPLOADA");
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        String listForProcesVariable = iFileService.filesStoreAndMap(files);
        runtimeService.setVariable(processInstanceId, "filesForUpload",listForProcesVariable);
        runtimeService.setVariable(processInstanceId, "handwriteFileName", files[0].getOriginalFilename());
        formService.submitTaskForm(taskId, new HashMap<>());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/download/{fileName}")
    public ResponseEntity downloadPDF(@PathVariable("fileName") String fileName) throws MalformedURLException {
        return iFileService.download(fileName);
    }

    @GetMapping("/load/{fileName}")
    public Resource loadFile(@PathVariable("fileName") String fileName) throws Exception {
        return iFileService.loadFile(fileName);
    }

}
