package com.lu.literaryassociation.services.camunda.plagiatCheck;

import com.lu.literaryassociation.entity.BoardMemberOpinion;
import com.lu.literaryassociation.repository.IBoardMemberOpinion;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CheckOpinions implements JavaDelegate {

    @Autowired
    private IBoardMemberOpinion iBoardMemberOpinion;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String title = (String) delegateExecution.getVariable("title");
        delegateExecution.setVariable("SameOpinion", areOpinionTheSame(title));
    }

    public Boolean areOpinionTheSame(String title){
        ArrayList<BoardMemberOpinion> boardMemberOpinions = (ArrayList<BoardMemberOpinion>) iBoardMemberOpinion.findBoardMemberOpinionByTitle(title);
        for(int i = 0 ; i< boardMemberOpinions.size() - 1; i++ ) {
            for (int j = i+1; j<boardMemberOpinions.size();j++){
                if(boardMemberOpinions.get(i).isPlagiat() != boardMemberOpinions.get(j).isPlagiat()){
                    return false;
                }
            }
        }

        return true;
    }


}
