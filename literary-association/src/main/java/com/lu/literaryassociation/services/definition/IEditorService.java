package com.lu.literaryassociation.services.definition;

import com.lu.literaryassociation.dto.request.FormFieldsDto;
import com.lu.literaryassociation.dto.request.FormSubmissionDto;

import java.util.List;

public interface IEditorService {

    FormFieldsDto getFormFieldsForReview(String processInstanceId);

    void submitWriterBookRequest(List<FormSubmissionDto> submitedFields, String processInstanceId, String reason);

    FormFieldsDto getPlagiatForm(String processInstanceId);

    void submitPlagiarismCheck(List<FormSubmissionDto> submitedFields, String processInstanceId, String reason, boolean sendToBetaReaders);
}
