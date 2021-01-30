package com.lu.literaryassociation.services.camunda;

import com.lu.literaryassociation.entity.ConfirmationToken;
import com.lu.literaryassociation.entity.Reader;
import com.lu.literaryassociation.entity.User;
import com.lu.literaryassociation.entity.Writer;
import com.lu.literaryassociation.repository.IUserRepository;
import com.lu.literaryassociation.services.implementation.ConfirmationTokenService;
import com.lu.literaryassociation.services.implementation.ReaderService;
import com.lu.literaryassociation.services.implementation.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CamundaConfirationToken implements JavaDelegate {

    private final ConfirmationTokenService confirmationTokenService;
    private final ReaderService  readerService;
    private final UserService userService;
    private final IUserRepository userRepository;

    public CamundaConfirationToken(ConfirmationTokenService confirmationTokenService, ReaderService readerService, UserService userService, IUserRepository userRepository) {
        this.confirmationTokenService = confirmationTokenService;
        this.readerService = readerService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

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
        String userType = (String) execution.getVariable("userType");
        if(userType.equals("reader")){
            Reader user = readerService.findReaderByUsername(confirmationToken.getUserEntity().getUsername());
            user.setApproved(true);
            userService.saveUser(user);
        }else{
            User user = userRepository.findOneByUsername(confirmationToken.getUserEntity().getUsername());
            userService.saveUser(user);
        }

        //remove confirmationTOken
        confirmationTokenService.delete(confirmationToken);
    }
}
