package com.lu.literaryassociation.repository;

import com.lu.literaryassociation.entity.LiteraryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ILiteraryUserRepository extends JpaRepository<LiteraryUser, UUID> {

    LiteraryUser findOneById(UUID id);

    LiteraryUser findOneByUsername(String username);
}
