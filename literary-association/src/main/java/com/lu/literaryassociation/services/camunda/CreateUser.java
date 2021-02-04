package com.lu.literaryassociation.services.camunda;

import com.lu.literaryassociation.entity.BetaReader;
import com.lu.literaryassociation.entity.Genre;
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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
        String city = (String)execution.getVariable("city");
        String country = (String)execution.getVariable("country");
        String genres = (String) execution.getVariable("genre");
        boolean betaReader = false;
        if(execution.hasVariable("betaReader")){
            betaReader = true;
        }


        System.out.println("USERNAME: "+ username+ "    Password: "+ password  + " " + email) ;
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
        execution.setVariable("username", username);

        //kreiramo naseg usera u bazi
        System.out.println("KREIRAMO USERA U BAZI");
        if(execution.hasVariable("betaReader")){
            com.lu.literaryassociation.entity.Reader customUser = new com.lu.literaryassociation.entity.Reader();
            customUser.setUsername(username);
            customUser.setPassword(_passwordEncoder.encode(password));
            customUser.setFirstName(firstname);
            customUser.setEmail(email);
            customUser.setApproved(false);
            if((Boolean) execution.getVariable("betaReader")){
                BetaReader beta = new BetaReader();
                //OVDE TREBA SETOVATI ZANR ZA BETA READERA
                System.out.println("Setujem zanr");
                Set<Genre> genreSet = new HashSet<Genre>();
                String[] parts = genres.split(";");
                for(int i=0;i<parts.length;i++){
                    System.out.println(parts[i]);
                    Genre g = iGenreService.getGenreByName(parts[i]);
                    System.out.println(g.getCode()+" "+g.getGenreName());
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
            execution.setVariable("userType", "reader");
            //Create conformationToken which will be send to email
            String tokenUUID = UUID.randomUUID().toString();
            confirmationTokenService.save(customUser, tokenUUID);
        }else{
            com.lu.literaryassociation.entity.Writer customUser = new com.lu.literaryassociation.entity.Writer();
            execution.setVariable("userType", "writer");
            customUser.setUsername(username);
            customUser.setPassword(_passwordEncoder.encode(password));
            customUser.setFirstName(firstname);
            customUser.setEmail(email);
            customUser.setCity(city);
            customUser.setCountry(country);
            customUser.setApproved(false);
            customUser.setUserType(UserType.WRITER);
            Set<Genre> genreSet = new HashSet<Genre>();
            String[] parts = genres.split(";");
            for(int i=0;i<parts.length;i++){
                System.out.println(parts[i]);
                Genre g = iGenreService.getGenreByName(parts[i]);
                System.out.println(g.getCode()+" "+g.getGenreName());
                genreSet.add(g);
            }
            customUser.setGenres(genreSet);
            iUserRepository.save(customUser);

            //Create conformationToken which will be send to email
            String tokenUUID = UUID.randomUUID().toString();
            confirmationTokenService.save(customUser, tokenUUID);
        }

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
