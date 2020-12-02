package com.lu.literaryassociation.repository;

import com.lu.literaryassociation.entity.Handwriting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IHandwritingRepository extends JpaRepository<Handwriting, UUID> {
}
