package com.lu.literaryassociation.repository;

import com.lu.literaryassociation.entity.CommitteeVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ICommitteeVoteRepository extends JpaRepository<CommitteeVote, UUID> {

    List<CommitteeVote> findByProcessInstanceId(String processInstanceId);

}
