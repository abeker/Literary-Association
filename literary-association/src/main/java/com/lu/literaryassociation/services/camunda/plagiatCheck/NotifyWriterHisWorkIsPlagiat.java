package com.lu.literaryassociation.services.camunda.plagiatCheck;

import com.lu.literaryassociation.services.mail.EmailService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifyWriterHisWorkIsPlagiat implements JavaDelegate {

    @Autowired
    private EmailService emailService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Here");
        String writer = (String) delegateExecution.getVariable("writer");
        emailService.sendEmail(writer,"Your work is voted as plagiat!",
                "Sorry, but we need to inform you that your work is voted as plagati. Therefore" +
                        "it won't be released. Please modify it, or write new one on your own." +
                        "Thanks, sincelery" +
                        "Your Literary Association"
        );
    }
}
