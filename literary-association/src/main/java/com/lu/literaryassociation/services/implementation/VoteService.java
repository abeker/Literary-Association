package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.entity.CommitteeVote;
import com.lu.literaryassociation.repository.ICommitteeVoteRepository;
import com.lu.literaryassociation.services.definition.IVoteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoteService implements IVoteService {

    private final ICommitteeVoteRepository iCommitteeVoteRepository;

    public VoteService(ICommitteeVoteRepository iCommitteeVoteRepository) {
        this.iCommitteeVoteRepository = iCommitteeVoteRepository;
    }


    public CommitteeVote saveVote(CommitteeVote committeeVote){
       return iCommitteeVoteRepository.save(committeeVote);
    }


    public int getCounterForNewVote(int committeeSize, String processID){
        int counter = 0;  //oznacava broj instanciranja podprocesa odobravanja reg
        List<CommitteeVote> committeeVotes = iCommitteeVoteRepository.findByProcessInstanceId(processID);
        if(committeeVotes.size()<committeeSize){
            counter = 1;
        }else if(committeeVotes.size()<committeeSize*2){
            counter = 2;
        }else{
            counter = 3;
        }
        return counter;
    }


    public List<CommitteeVote> votesForCheck (int commiteeSize, String processID){
        System.out.println("votes for check");
        System.out.println(processID);
        List<CommitteeVote> lastVotes = new ArrayList<>();
        List<CommitteeVote> committeeVotes = iCommitteeVoteRepository.findByProcessInstanceId(processID);
        int loopCounter = (committeeVotes.size()) / commiteeSize;
        for(CommitteeVote cv : committeeVotes){
            if(cv.getCounter() == loopCounter){
                lastVotes.add(cv);
            }
        }
        return lastVotes;
    }



}
