package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.entity.ConfirmationToken;
import com.lu.literaryassociation.entity.User;
import com.lu.literaryassociation.repository.IConformationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Calendar;

@Service
public class ConfirmationTokenService {
    @Autowired
    IConformationTokenRepository iConformationTokenRepository;

    @Transactional
    public ConfirmationToken findByToken(String token){
        return iConformationTokenRepository.findByConfirmationToken(token);
    }

    @Transactional
    public ConfirmationToken findByUser(User user){
        return iConformationTokenRepository.findByUserEntity(user);
    }

    @Transactional
    public void save(User user, String token){
        ConfirmationToken confirmationToken = new ConfirmationToken(token,user);
       //set expiry date to 24h
        confirmationToken.setExpiryDate(calculateExpiryDate(24*60));
        iConformationTokenRepository.save(confirmationToken);
    }

    @Transactional
    public void delete(ConfirmationToken ct){
         iConformationTokenRepository.delete(ct);
    }

    private Timestamp calculateExpiryDate(int expiryTimeMinutes){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE,expiryTimeMinutes);
        return new Timestamp(cal.getTime().getTime());
    }
}
