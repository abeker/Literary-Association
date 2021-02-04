package com.lu.literaryassociation.repository;

import com.lu.literaryassociation.entity.BoardMemberOpinion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IBoardMemberOpinion extends JpaRepository<BoardMemberOpinion, UUID> {
    List<BoardMemberOpinion> findBoardMemberOpinionByTitle(String title);
}
