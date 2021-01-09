package com.lu.literaryassociation.repository;

import com.lu.literaryassociation.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IMembershipRepository extends JpaRepository<Membership, UUID> {
}
