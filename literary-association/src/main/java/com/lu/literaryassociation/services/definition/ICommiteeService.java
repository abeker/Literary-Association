package com.lu.literaryassociation.services.definition;

import com.lu.literaryassociation.entity.CommitteeMember;

import java.util.List;

public interface ICommiteeService {

     List<CommitteeMember> getAll();

     CommitteeMember getCommitteeByUserName(String username);

}
