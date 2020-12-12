package com.lu.literaryassociation.services.camunda;

import com.lu.literaryassociation.entity.BetaReader;
import com.lu.literaryassociation.entity.ConfirmationToken;
import com.lu.literaryassociation.entity.FormSubmissionDto;
import com.lu.literaryassociation.repository.IConformationTokenRepository;
import com.lu.literaryassociation.repository.IUserRepository;
import com.lu.literaryassociation.services.implementation.ConfirmationTokenService;
import com.lu.literaryassociation.util.enums.UserType;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CreateUser implements JavaDelegate {

    @Autowired
    IdentityService identityService;
    @Autowired
    IUserRepository iUserRepository;

    @Autowired
    PasswordEncoder _passwordEncoder;

    @Autowired
    ConfirmationTokenService confirmationTokenService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        User u = execution.getProcessEngine().getIdentityService().newUser("");
        String username = (String)execution.getVariable("username");
        String firstname = (String)execution.getVariable("firstname");
        String lastname = (String)execution.getVariable("lastname");
        String email = (String)execution.getVariable("email");
        String password = (String) execution.getVariable("password");
        String confirm_pass = (String)execution.getVariable("c_password");
        if (!password.equals(confirm_pass)){
            return;
        }
        String city = (String)execution.getVariable("city");
        String country = (String)execution.getVariable("country");
        boolean betaReader = (Boolean) execution.getVariable("betaReader");

        System.out.println("USERNAME: "+ username+ "    Password: "+ password  + " " + email+ "Beta reader: " + betaReader) ;

        //Kreiramo camundinog ugradnjenog usera
        User user = identityService.newUser("");
        user.setId(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        System.out.println("CUAVAMO USERA:");

        //OVAJ DEO NE ZNAM DAL JE NEOPHODAN
        identityService.saveUser(user);

        //kreiramo naseg usera u bazi
        com.lu.literaryassociation.entity.Reader customUser = new com.lu.literaryassociation.entity.Reader();
        customUser.setUsername(username);
        customUser.setPassword(password);
        customUser.setFirstName(firstname);
        customUser.setEmail(email);
        customUser.setApproved(false);
        if(betaReader){
            BetaReader beta = new BetaReader();
            //OVDE TREBA SETOVATI ZANR ZA BETA READERA
            // .....
            beta.setReader(customUser);
            customUser.setBetaReader(beta);
        }
        customUser.setCity(city);
        customUser.setCountry(country);
        customUser.setUserType(UserType.READER);

        iUserRepository.save(customUser);

        //Create conformationToken which will be send to email
        String tokenUUID = UUID.randomUUID().toString();
        confirmationTokenService.save(customUser, tokenUUID);
    }
}



//        var user = execution.getProcessEngine().getIdentityService().newUser(username);
//
//        user.firstName = firstName;
//        user.lastName = lastName;
//        user.email = email;
//        user.password = password;
//
//        execution.getProcessEngine().getIdentityService().saveUser(user);
//        execution.getProcessEngine().getIdentityService().createMembership(user.id, "readers");
