package com.lu.literaryassociation.services.camunda;

import com.lu.literaryassociation.entity.ConfirmationToken;
import com.lu.literaryassociation.entity.User;
import com.lu.literaryassociation.repository.IConformationTokenRepository;
import com.lu.literaryassociation.repository.IUserRepository;
import com.lu.literaryassociation.services.implementation.ConfirmationTokenService;
import com.lu.literaryassociation.services.mail.EmailService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.UUID;

@Service
public class SendActivationLink implements JavaDelegate {
    @Autowired
    IConformationTokenRepository iConformationTokenRepository;

    @Autowired
    IUserRepository iUserRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    ConfirmationTokenService confirmationTokenService;

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        String email = (String) execution.getVariable("email");
        String username = (String)execution.getVariable("username");
        User userEntity = iUserRepository.findOneByUsername(username);
        ConfirmationToken confirmationToken = confirmationTokenService.findByUser(userEntity);
        if (confirmationToken == null){
            return;
        }
        String processInstanceId = execution.getProcessInstanceId();

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setTo(email);
        helper.setSubject("email address verification");
        helper.setText("To confirm your account, please click here : "
                +"http://localhost:8084/welcome/confirm-account/"+processInstanceId+"?token="+confirmationToken.getConfirmationToken());
        javaMailSender.send(message);
    }
}
