package com.lu.literaryassociation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormSubmissionDto implements Serializable {
	
	private	String fieldId;
	private String fieldValue;
	
}
