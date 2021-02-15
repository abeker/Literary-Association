package com.lu.literaryassociation.services.camunda.publishBook;

import com.lu.literaryassociation.dto.request.FormSubmissionDto;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaveMistakes implements JavaDelegate {

    private final RuntimeService runtimeService;
    private final FormService formService;

    public SaveMistakes(RuntimeService runtimeService, FormService formService) {
        this.runtimeService = runtimeService;
        this.formService = formService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("save mistakes");
        String mistakes = "";
        boolean isAllOk = true;

        List<FormSubmissionDto> submissionDtoList = (List<FormSubmissionDto>) execution.getVariable("LectorReview");

        for(FormSubmissionDto fd: submissionDtoList){
            System.out.println(fd.getFieldValue());
            if(fd.getFieldId().equals("mistakes")){
                mistakes = fd.getFieldValue();
            }else if(fd.getFieldId().equals("isOk")){
                if(fd.getFieldValue() == "true"){
                    isAllOk = true;
                }
            }
        }

        execution.setVariable("LectorMistakes", mistakes);
        execution.setVariable("isAllOk", isAllOk);
    }
}
