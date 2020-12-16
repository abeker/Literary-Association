package com.lu.literaryassociation.repository;

import com.lu.literaryassociation.entity.Genre;
import com.lu.literaryassociation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IGenreRepository extends JpaRepository<Genre, UUID> {

     Genre findByGenreName(String genreName);

}
