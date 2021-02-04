package com.lu.literaryassociation.services.camunda;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.FormFieldValidationConstraint;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ValidatFormSerivce implements JavaDelegate {

    private final IdentityService identityService;
    private final TaskService taskService;
    private final FormService formService;

    public ValidatFormSerivce(IdentityService identityService, TaskService taskService, FormService formService) {
        this.identityService = identityService;
        this.taskService = taskService;
        this.formService = formService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        System.out.println("Usla u validaciju");
        Map<String, Object> varaibles = execution.getVariables();
        Task task = taskService.createTaskQuery().processInstanceId(execution.getProcessInstanceId()).list().get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();

        for(FormField fp : properties) {
            for(FormFieldValidationConstraint constraint: fp.getValidationConstraints()){
                switch (constraint.getName()){
                    case "required":
                        if(varaibles.get(fp.getId()).equals("")) {
                            execution.setVariable("valid",false);
                            throw new BpmnError("Wrong input");
                        }
                        break;
                    case "minLength":
                        if((varaibles.get(fp.getId())).toString().length() < Integer.parseInt(constraint.getConfiguration().toString()))
                            execution.setVariable("valid",false);
                        break;
                    default:
                        execution.setVariable("valid", true);
                        break;
                }
            }
        }

       /* if(varaibles.get("password").equals(varaibles.get("c_password"))){
            throw new BpmnError("Wrong input");
        }*/

        String username = (String) varaibles.get("username");

        User user = identityService.createUserQuery().userId(username).singleResult();
        if(user != null){
            execution.setVariable("valid",false);
            return;
        }
        execution.setVariable("valid",true);
        System.out.println("validirala");
    }


}
