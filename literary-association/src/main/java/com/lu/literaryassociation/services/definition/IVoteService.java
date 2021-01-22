package com.lu.literaryassociation.services.definition;

import com.lu.literaryassociation.entity.CommitteeMember;
import com.lu.literaryassociation.entity.CommitteeVote;

import java.util.List;

public interface IVoteService {

     CommitteeVote saveVote(CommitteeVote committeeVote);

     int getCounterForNewVote(int committeeSize, String processID);

     List<CommitteeVote> votesForCheck (int commiteeSize, String processID);


}
