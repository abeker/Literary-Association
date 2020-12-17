package com.lu.literaryassociation.services.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CamundaValidator implements FormFieldValidator {

    @Override
    public boolean validate(Object submittedValue, FormFieldValidatorContext validatorContext) {
        DelegateExecution execution = validatorContext.getExecution();
        if(submittedValue == null) {
            TypedValue value = validatorContext.getVariableScope().getVariableTyped(validatorContext.getFormFieldHandler().getId());
            execution.setVariable("valid",(value != null && value.getValue() != null));
            return (value != null && value.getValue() != null);
        } else {
            if (submittedValue instanceof String) {
                execution.setVariable("valid",submittedValue != null && !((String)submittedValue).isEmpty());
                return submittedValue != null && !((String)submittedValue).isEmpty();
            } else {
                execution.setVariable("valid",submittedValue!=null);
                return submittedValue != null;
            }
        }
    }
}
