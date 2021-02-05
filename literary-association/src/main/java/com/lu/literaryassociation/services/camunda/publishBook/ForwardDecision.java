package com.lu.literaryassociation.services.camunda.publishBook;

import com.lu.literaryassociation.dto.request.FormSubmissionDto;
import com.lu.literaryassociation.repository.ILectorRepository;
import com.lu.literaryassociation.services.definition.IUserService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForwardDecision implements JavaDelegate {

    private final IdentityService identityService;
    private final TaskService taskService;
    private final RuntimeService runtimeService;
    private final FormService formService;
    private final IUserService iUserService;

    public ForwardDecision(IdentityService identityService, TaskService taskService, RuntimeService runtimeService, FormService formService, ILectorRepository iLectorRepository, IUserService iUserService) {
        this.identityService = identityService;
        this.taskService = taskService;
        this.runtimeService = runtimeService;
        this.formService = formService;
        this.iUserService = iUserService;
    }


    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("forward decision");
        boolean isMoreEditsNeeded = true;

        List<FormSubmissionDto> submissionDtoList = (List<FormSubmissionDto>) execution.getVariable("MoreEditsOrForward");

        for(FormSubmissionDto fd: submissionDtoList){
            System.out.println(fd.getFieldValue());
            if(fd.getFieldValue().equals("send_to_lector")){
                isMoreEditsNeeded = false;
            }
        }

        execution.setVariable("isMoreEditsNeeded", isMoreEditsNeeded);
        execution.setVariable("lector", "lector"); //ovde izvuci username lectora iz baze

    }
}
