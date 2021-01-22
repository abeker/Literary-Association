package com.lu.literaryassociation.services.camunda.publishBook;

import com.lu.literaryassociation.entity.BetaReaderComment;
import com.lu.literaryassociation.services.definition.IReaderService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetBetaComments implements JavaDelegate {

    private final IReaderService iReaderService;

    public GetBetaComments(IReaderService iReaderService) {
        this.iReaderService = iReaderService;
    }


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("preuzimanje beta reader comentara" + delegateExecution.getProcessInstanceId());

        String processId = delegateExecution.getProcessInstanceId();
        List<BetaReaderComment> betaReaderCommentList = iReaderService.getBetaReaderCommentByProcess(processId);

        //kako slati komentare

    }
}
