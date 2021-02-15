package com.lu.literaryassociation.services.camunda.writerReg;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class DeactivateAccount implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String writerUsername = (String) delegateExecution.getVariable("username");
        System.out.println("Usla u deactivate accounta writera" + writerUsername);
    }


}
