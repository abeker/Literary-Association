package com.lu.literaryassociation.services.camunda.publishBook;

import com.lu.literaryassociation.services.definition.IReaderService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetBetaReaders implements JavaDelegate {

    private final IReaderService iReaderService;

    public GetBetaReaders(IReaderService iReaderService) {
        this.iReaderService = iReaderService;
    }


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println("Usla u izlacenje beta readera");
        String genreName = "poetry"; //ovo izvuci iz procesne varijable zanra knjige
        List<String> betaReadersList = iReaderService.findBetaReaderInfoByGenre(genreName);
        String chooseBetaReaders = "";

        for(String br : betaReadersList){
            chooseBetaReaders = chooseBetaReaders.concat(br.concat(";"));
        }

        delegateExecution.setVariable("choosenBetaReaders", chooseBetaReaders);

    }


}
