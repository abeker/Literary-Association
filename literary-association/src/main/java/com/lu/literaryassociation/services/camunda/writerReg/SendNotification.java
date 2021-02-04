package com.lu.literaryassociation.services.camunda.writerReg;

import com.lu.literaryassociation.entity.User;
import com.lu.literaryassociation.repository.IUserRepository;
import com.lu.literaryassociation.services.mail.EmailService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class SendNotification implements JavaDelegate {

    private final EmailService emailService;

    private final JavaMailSender javaMailSender;

    private final IUserRepository iUserRepository;

    public SendNotification(EmailService emailService, JavaMailSender javaMailSender, IUserRepository iUserRepository) {
        this.emailService = emailService;
        this.javaMailSender = javaMailSender;
        this.iUserRepository = iUserRepository;
    }


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Usla u slanje notifikacije piscu");
        String processId = delegateExecution.getProcessInstanceId();
        String type = (String) delegateExecution.getVariable("votedDecision");
        String email = (String) delegateExecution.getVariable("email");
        String username = (String) delegateExecution.getVariable("username");
        System.out.println(type);
        if(type.equals("moreMaterials")){
            sendMailForMoreMaterial(processId, email);
        }else if(type.equals("approved")){
            activateAcount(username);
            sendMailForApproved(processId,email);
        }else{
            sendMailForDenied(processId, email);
        }

    }


    public void sendMailForMoreMaterial(String processId, String email) throws MessagingException {
        System.out.println("Saljem mejl za jos materijala");
        String subject = "need more materials";
        String text = "Please, upload more materials: "+
                      "http://localhost:4200/register/fileUpload";
        emailService.generalSendEmail(email, subject, text);
    }

    public void sendMailForApproved(String processId, String email) throws MessagingException {
        System.out.println("Saljem mejl za odobren aacount");
        String subject = "approved";
        String text = "Your account is approved)";
        emailService.generalSendEmail(email, subject, text);
    }

    public void sendMailForDenied(String processId, String email) throws MessagingException {
        System.out.println("Saljem mejl za odbijen aacount");
        String subject = "denied";
        String text = "Your account is denied";
        emailService.generalSendEmail(email, subject, text);
    }

    public void activateAcount(String username){
        User u = iUserRepository.findOneByUsername(username);
        u.setApproved(true);
        iUserRepository.save(u);
    }

}