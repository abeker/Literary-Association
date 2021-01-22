package com.lu.literaryassociation.repository;

import com.lu.literaryassociation.entity.BetaReaderComment;
import com.lu.literaryassociation.entity.CommitteeVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IBetaReaderCommentRepository extends JpaRepository<BetaReaderComment, UUID> {

    List<BetaReaderComment> findByProcessInstance(String processInstance);

}
