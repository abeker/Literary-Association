package com.lu.literaryassociation.repository;

import com.lu.literaryassociation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {
    User findOneByUsername(String username);
    User findOneById(UUID uuid);
}
