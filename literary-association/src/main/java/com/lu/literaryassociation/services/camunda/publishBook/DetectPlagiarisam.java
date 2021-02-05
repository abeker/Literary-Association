package com.lu.literaryassociation.services.camunda.publishBook;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class DetectPlagiarisam implements JavaDelegate {

    private final RuntimeService runtimeService;

    public DetectPlagiarisam(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
         System.out.println("detect plagiarism");
         String instanceId = delegateExecution.getProcessInstanceId();
         System.out.println(instanceId);

         Random r = new Random();
         int low = 0;
         int high = 100;
         int result = r.nextInt(high-low) + low;
         System.out.println(result);
         delegateExecution.setVariable("plagiarismPrecent", result);

         System.out.println("finish");
    }
}
