package com.lu.literaryassociation.services.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;


    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(String email,String processInstanceId, String confirmationToken) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setTo(email);
        helper.setSubject("email address verification");
        helper.setText("To confirm your account, please click here : "
                +"http://localhost:8084/welcome/confirm-account/"+processInstanceId+"?token="+confirmationToken);
        javaMailSender.send(message);
    }


    @Async
    public void generalSendEmail(String email,String subject, String text) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(text);
        javaMailSender.send(message);
    }

    public void sendmail(String receiverEmail){

    }
}
