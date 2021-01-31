package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.entity.BoardMemberOpinion;
import com.lu.literaryassociation.repository.IBoardMemberOpinion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BoardMemberOpinionService {
    @Autowired
    private IBoardMemberOpinion iBoardMemberOpinion;

    public ArrayList<BoardMemberOpinion> findByTitle(String title) {
        return (ArrayList<BoardMemberOpinion>) iBoardMemberOpinion.findBoardMemberOpinionByTitle(title);
    }

    public BoardMemberOpinion save(BoardMemberOpinion boardMemberOpinion){
        return iBoardMemberOpinion.save(boardMemberOpinion);
    }
}
