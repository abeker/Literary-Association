package com.lu.literaryassociation.repository;

import com.lu.literaryassociation.entity.CommitteeMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ICommitteeMemberRepository extends JpaRepository<CommitteeMember, UUID> {

}
