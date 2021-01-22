package com.lu.literaryassociation.repository;

import com.lu.literaryassociation.entity.Reader;
import com.lu.literaryassociation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IReaderRepository extends JpaRepository<Reader, UUID> {

    Reader findOneByUsername(String username);

}
