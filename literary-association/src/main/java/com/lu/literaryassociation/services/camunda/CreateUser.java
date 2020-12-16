package com.lu.literaryassociation.services.camunda;

import camundajar.impl.scala.Array;
import com.lu.literaryassociation.entity.BetaReader;
import com.lu.literaryassociation.entity.ConfirmationToken;
import com.lu.literaryassociation.entity.FormSubmissionDto;
import com.lu.literaryassociation.entity.Genre;
import com.lu.literaryassociation.repository.IConformationTokenRepository;
import com.lu.literaryassociation.repository.IUserRepository;
import com.lu.literaryassociation.services.definition.IGenreService;
import com.lu.literaryassociation.services.implementation.ConfirmationTokenService;
import com.lu.literaryassociation.util.enums.UserType;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CreateUser implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    IUserRepository iUserRepository;

    @Autowired
    IGenreService iGenreService;

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
        String genres = (String) execution.getVariable("genre");

        System.out.println("USERNAME: "+ username+ "    Password: "+ password  + " " + email+ "Beta reader: " + betaReader) ;
        System.out.println("GENRES"+ genres);

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
        System.out.println("KREIRAMO USERA U BAZI");
        com.lu.literaryassociation.entity.Reader customUser = new com.lu.literaryassociation.entity.Reader();
        customUser.setUsername(username);
        customUser.setPassword(password);
        customUser.setFirstName(firstname);
        customUser.setEmail(email);
        customUser.setApproved(false);
        if(betaReader){
            BetaReader beta = new BetaReader();
            //OVDE TREBA SETOVATI ZANR ZA BETA READERA
           // System.out.println("Setujem zanr");
            Set<Genre> genreSet = new HashSet<Genre>();
            String[] parts = genres.split(";");
            for(int i=0;i<parts.length;i++){
                Genre g = iGenreService.getGenreByName(parts[i]);
               // System.out.println(g.getCode()+" "+g.getGenreName());
                genreSet.add(g);
            }
             beta.setGenres(genreSet);
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
