package com.lu.literaryassociation.services.camunda.publishBook;

import com.lu.literaryassociation.entity.BetaReader;
import com.lu.literaryassociation.entity.BetaReaderComment;
import com.lu.literaryassociation.entity.Reader;
import com.lu.literaryassociation.services.definition.IReaderService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class CalculatePenalty implements JavaDelegate {


    private final IReaderService iReaderService;

    public CalculatePenalty(IReaderService iReaderService) {
        this.iReaderService = iReaderService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println("Calculate penalty");
        String username = (String) delegateExecution.getVariable("betaReader");
        System.out.println(username);
        Reader reader = iReaderService.findReaderByUsername(username);
        BetaReader betaReader = reader.getBetaReader();
        int newPenalty = betaReader.getPenaltyPoint() + 1;
        betaReader.setPenaltyPoint(newPenalty);
        betaReader = iReaderService.updateBetaReader(betaReader);
        delegateExecution.setVariable("penaltyPoint", newPenalty);

    }


}
