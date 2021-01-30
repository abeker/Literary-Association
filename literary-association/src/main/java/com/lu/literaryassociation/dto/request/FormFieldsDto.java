package com.lu.literaryassociation.dto.request;

import lombok.Data;
import org.camunda.bpm.engine.form.FormField;

import java.util.List;

@Data
public class FormFieldsDto {

	private String taskId;
	private List<FormField> formFields;
	private String processInstanceId;

	public FormFieldsDto(String taskId, String processInstanceId, List<FormField> formFields) {
		super();
		this.taskId = taskId;
		this.formFields = formFields;
		this.processInstanceId = processInstanceId;
	}

}
