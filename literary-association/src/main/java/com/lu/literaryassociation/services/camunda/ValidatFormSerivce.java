package com.lu.literaryassociation.services.camunda;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ValidatFormSerivce implements JavaDelegate {
    @Autowired
    IdentityService identityService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Map<String, Object> varaibles = execution.getVariables();
        String username = (String) varaibles.get("username");
        if (username.equals("")){
            execution.setVariable("valid",false);
            return;
        }
        String email = (String) varaibles.get("email");
        if (email.equals("")){
            execution.setVariable("valid",false);
            return;
        }
        String password = (String) varaibles.get("password");
        if (password.equals("")){
            execution.setVariable("valid",false);
            return;
        }
        User user = identityService.createUserQuery().userId(username).singleResult();
        if(user != null){
            execution.setVariable("valid",false);
            return;
        }
        execution.setVariable("valid",true);
    }
}
