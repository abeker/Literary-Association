package com.lu.literaryassociation.services.camunda.publishBook;

import com.lu.literaryassociation.dto.request.FormSubmissionDto;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaveConfirmation implements JavaDelegate {

    private final RuntimeService runtimeService;
    private final FormService formService;

    public SaveConfirmation(RuntimeService runtimeService, FormService formService) {
        this.runtimeService = runtimeService;
        this.formService = formService;
    }


    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("save confirmation");
        String finalComments = "";
        boolean isBookApproved = true;

        List<FormSubmissionDto> submissionDtoList = (List<FormSubmissionDto>) execution.getVariable("EditorConfirmation");

        for(FormSubmissionDto fd: submissionDtoList){
            System.out.println(fd.getFieldValue());
            if(fd.getFieldId().equals("finalComments")){
                finalComments = fd.getFieldValue();
            }else if(fd.getFieldId().equals("finalApprove")){
                if(fd.getFieldValue() == "true"){
                    isBookApproved = true;
                }
            }
        }

        execution.setVariable("finalComments", finalComments);
        execution.setVariable("isBookApproved", isBookApproved);
    }
}
