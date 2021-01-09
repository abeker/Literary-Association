package com.lu.literaryassociation.services.camunda;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;

public class StartProcess implements JavaDelegate {
    @Autowired
    RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("Popuni_formu");
        execution.setVariable("processInstanceId", pi.getId());
    }
}
