package com.lu.literaryassociation.services.camunda.plagiatCheck;

import com.lu.literaryassociation.entity.CommitteeMember;
import com.lu.literaryassociation.repository.ICommitteeMemberRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GetAllBoardMembers implements JavaDelegate {
    @Autowired
    private ICommitteeMemberRepository iCommitteeMemberRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ArrayList<CommitteeMember> committeeMembers = (ArrayList<CommitteeMember>) iCommitteeMemberRepository.findAll();
        ArrayList<String>comiteeMembersString = new ArrayList<String>() ;
        for(CommitteeMember cm : committeeMembers){
            comiteeMembersString.add(cm.getUsername());
        }
        delegateExecution.setVariable("comiteeMembersSize", comiteeMembersString.size());
        delegateExecution.setVariable("comiteeMembers", comiteeMembersString);
    }
}
