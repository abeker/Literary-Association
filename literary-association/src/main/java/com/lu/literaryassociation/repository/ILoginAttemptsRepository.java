package com.lu.literaryassociation.repository;

import com.lu.literaryassociation.entity.LoginAttempts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ILoginAttemptsRepository extends JpaRepository<LoginAttempts, UUID> {

    LoginAttempts findOneByIpAddress(String ipAddress);

}
