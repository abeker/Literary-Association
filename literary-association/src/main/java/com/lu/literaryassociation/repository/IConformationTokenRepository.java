package com.lu.literaryassociation.repository;

import com.lu.literaryassociation.entity.ConfirmationToken;
import com.lu.literaryassociation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IConformationTokenRepository extends JpaRepository<ConfirmationToken,Long> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
    ConfirmationToken findByUserEntity(User user);
}
