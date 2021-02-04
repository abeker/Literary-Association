package com.lu.literaryassociation.services.camunda.publishBook;

import com.lu.literaryassociation.dto.request.FormSubmissionDto;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.task.Task;
import org.camunda.feel.syntaxtree.For;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForwardDecision implements JavaDelegate {

    private final IdentityService identityService;
    private final TaskService taskService;
    private final RuntimeService runtimeService;
    private final FormService formService;

    public ForwardDecision(IdentityService identityService, TaskService taskService, RuntimeService runtimeService, FormService formService) {
        this.identityService = identityService;
        this.taskService = taskService;
        this.runtimeService = runtimeService;
        this.formService = formService;
    }


    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("forward decision");
        boolean isMoreEditsNeeded = true;

        Task task = taskService.createTaskQuery().processInstanceId(execution.getProcessInstanceId()).list().get(0);
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();

        for(FormField formField: properties){
            if(formField.getValue().equals("send_to_lector")){
                isMoreEditsNeeded = false;
            }
        }

        execution.setVariable("isMoreEditsNeeded", isMoreEditsNeeded);

    }
}
