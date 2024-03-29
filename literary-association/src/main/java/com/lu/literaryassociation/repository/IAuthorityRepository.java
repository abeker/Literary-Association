package com.lu.literaryassociation.repository;

import com.lu.literaryassociation.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAuthorityRepository extends JpaRepository<Authority, UUID> {

    Authority findByName(String name);

}
