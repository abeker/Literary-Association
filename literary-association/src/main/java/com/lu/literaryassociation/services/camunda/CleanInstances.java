package com.lu.literaryassociation.services.camunda;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.history.HistoricDecisionInstance;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CleanInstances implements JavaDelegate {

    private final HistoryService historyService;

    public CleanInstances(HistoryService historyService) {
        this.historyService = historyService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println("delete instances");
        HistoryService historyService = delegateExecution.getProcessEngineServices().getHistoryService();

        List<HistoricDecisionInstance> decisionDefinitions = historyService.createHistoricDecisionInstanceQuery()
                .decisionDefinitionKey("decisionDefinitionKey").list();

        decisionDefinitions.stream().map(HistoricDecisionInstance::getId).forEach(
                historicDecisionInstanceId -> historyService
                        .deleteHistoricDecisionInstanceByInstanceId(historicDecisionInstanceId));
    }
}
