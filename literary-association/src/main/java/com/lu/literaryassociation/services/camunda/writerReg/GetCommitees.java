package com.lu.literaryassociation.services.camunda.writerReg;

import com.lu.literaryassociation.entity.CommitteeMember;
import com.lu.literaryassociation.entity.User;
import com.lu.literaryassociation.services.definition.ICommiteeService;
import com.lu.literaryassociation.services.definition.IUserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetCommitees implements JavaDelegate {

    private final ICommiteeService iCommiteeService;
    private final IUserService iUserService;

    public GetCommitees(ICommiteeService iCommiteeService, IUserService iUserService) {
        this.iCommiteeService = iCommiteeService;
        this.iUserService = iUserService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println("USLA U GET COMMITEES");

        List<CommitteeMember> committeeMemberList = iCommiteeService.getAll();
        ArrayList<String> commitees = new ArrayList<>();

        for (CommitteeMember cm: committeeMemberList) {
            User user = iUserService.getUserById(cm.getId());
            System.out.println(user.getUsername());
            commitees.add(user.getUsername());
        }

        delegateExecution.setVariable("commitees", commitees);
        delegateExecution.setVariable("numberOfCommitees", commitees.size());

    }
}
