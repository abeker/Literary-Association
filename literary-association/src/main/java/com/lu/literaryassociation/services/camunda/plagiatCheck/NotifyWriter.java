package com.lu.literaryassociation.services.camunda.plagiatCheck;

import com.lu.literaryassociation.config.EmailContext;
import com.lu.literaryassociation.services.mail.EmailService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifyWriter implements JavaDelegate {

    @Autowired
    private EmailService emailService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Here");
        String writer = (String) delegateExecution.getVariable("writer");
        emailService.sendEmail(writer,"Your work is being considerating as plagiat",
                "Sorry, but we need to check if your work is plagiat," +
                        " We honestly belive it isn't, but that's the process, so be patient! " +
                        "Thanks if forward" +
                        "Your Literary Association!"
                );
    }
}
