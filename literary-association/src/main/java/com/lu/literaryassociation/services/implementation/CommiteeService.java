package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.entity.CommitteeMember;
import com.lu.literaryassociation.repository.ICommitteeMemberRepository;
import com.lu.literaryassociation.services.definition.ICommiteeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommiteeService implements ICommiteeService {

    private final ICommitteeMemberRepository iCommitteeMemberRepository;

    public CommiteeService(ICommitteeMemberRepository iCommitteeMemberRepository) {
        this.iCommitteeMemberRepository = iCommitteeMemberRepository;
    }

    @Override
    public List<CommitteeMember> getAll(){
        return iCommitteeMemberRepository.findAll()
                .stream()
                .filter(committeeMember -> !committeeMember.isDeleted())
                .collect(Collectors.toList());
    }

    @Override
    public CommitteeMember getCommitteeByUserName(String username) {
        List<CommitteeMember> committeeMemberList = getAll();
        CommitteeMember committeeMember = new CommitteeMember();
        for(CommitteeMember cm: committeeMemberList){
             if(cm.getUsername().equals(username))
                 committeeMember = cm;
        }
        return committeeMember;
    }

}
