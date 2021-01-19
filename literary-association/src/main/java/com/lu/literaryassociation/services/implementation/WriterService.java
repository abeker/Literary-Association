package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.dto.request.FormFieldsDto;
import com.lu.literaryassociation.security.TokenUtils;
import com.lu.literaryassociation.services.definition.IWriterService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WriterService implements IWriterService {

    private final TokenUtils _tokenUtils;
    private final TaskService _taskService;
    private final FormService _formService;

    public WriterService(TokenUtils tokenUtils, TaskService taskService, FormService formService) {
        _tokenUtils = tokenUtils;
        _taskService = taskService;
        _formService = formService;
    }

    @Override
    public Map<String, Object> createMapFromToken(String token) {
        String username = _tokenUtils.getUsernameFromToken(token);
        Map<String, Object> variableMap = new HashMap<>();
        variableMap.put("writerUsername", username);
        return variableMap;
    }

    @Override
    public FormFieldsDto getFormFieldsForPublishPaper(String processInstanceId) {
        System.out.println("PROCES: " + processInstanceId);
        Task task = _taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);

        TaskFormData tfd = _formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFieldsDto(task.getId(),processInstanceId, properties);
    }
}
