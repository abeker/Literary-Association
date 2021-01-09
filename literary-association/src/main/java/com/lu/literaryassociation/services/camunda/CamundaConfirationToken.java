package com.lu.literaryassociation.services.camunda;

import com.lu.literaryassociation.entity.ConfirmationToken;
import com.lu.literaryassociation.entity.Reader;
import com.lu.literaryassociation.services.implementation.ConfirmationTokenService;
import com.lu.literaryassociation.services.implementation.ReaderService;
import com.lu.literaryassociation.services.implementation.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CamundaConfirationToken implements JavaDelegate {
    @Autowired
    ConfirmationTokenService confirmationTokenService;

    @Autowired
    ReaderService  readerService;

    @Autowired
    UserService userService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String token = (String) execution.getVariable("confirmationToken");
        System.out.println("TOKEN"+ token);
        ConfirmationToken confirmationToken = confirmationTokenService.findByToken(token);
        if(confirmationToken == null) {
            return;
        }
        //is it expired?
        if(confirmationToken.getExpiryDate().before(new Date())){
            return;
        }
        Reader user = readerService.findReaderByUsername(confirmationToken.getUserEntity().getUsername());
        user.setApproved(true);
        userService.saveUser(user);
        //remove confirmationTOken
        confirmationTokenService.delete(confirmationToken);
    }
}
