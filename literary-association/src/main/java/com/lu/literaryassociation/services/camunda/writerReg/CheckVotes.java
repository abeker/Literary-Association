package com.lu.literaryassociation.services.camunda.writerReg;

import com.lu.literaryassociation.entity.CommitteeVote;
import com.lu.literaryassociation.services.definition.ICommiteeService;
import com.lu.literaryassociation.services.definition.IVoteService;
import com.lu.literaryassociation.util.enums.WriterStatus;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckVotes implements JavaDelegate {

    private final IVoteService iVoteService;

    private final ICommiteeService iCommiteeService;

    public CheckVotes(IVoteService iVoteService, ICommiteeService iCommiteeService) {
        this.iVoteService = iVoteService;
        this.iCommiteeService = iCommiteeService;
    }


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Usla u check votes");
        String processId = delegateExecution.getProcessInstanceId();
        System.out.println(processId);
        int size = iCommiteeService.getAll().size();
        List<CommitteeVote> votes = iVoteService.votesForCheck(size,processId);

        int numberOfApproved = 0;
        int numberOfDenied = 0;
        int numberOfMoreMaterials = 0;

        for(CommitteeVote vote: votes){
               if(vote.getWriterStatus() == WriterStatus.APPROVED){
                   numberOfApproved ++;
               }else if(vote.getWriterStatus() == WriterStatus.DENIED){
                   numberOfDenied ++;
               }else{
                   numberOfMoreMaterials ++;
               }
        }

        String votedDecision = "";
        if(numberOfApproved == size){
              votedDecision = "approved";
        }else if(numberOfMoreMaterials > 0){
              votedDecision = "moreMaterials";
        }else{
              votedDecision = "denied";
        }

        System.out.println(votedDecision);
        delegateExecution.setVariable("votedDecision", votedDecision);

    }
}
