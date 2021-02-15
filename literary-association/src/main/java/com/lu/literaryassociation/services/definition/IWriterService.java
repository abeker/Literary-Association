package com.lu.literaryassociation.services.definition;

import com.lu.literaryassociation.dto.request.BookCreate;
import com.lu.literaryassociation.dto.request.FormFieldsDto;
import com.lu.literaryassociation.dto.request.FormSubmissionDto;
import com.lu.literaryassociation.dto.response.PublishPaperForm;
import com.lu.literaryassociation.dto.response.WriterBookCreate;
import com.lu.literaryassociation.entity.Genre;
import org.camunda.bpm.engine.runtime.ProcessInstance;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IWriterService {
    Map<String, Object> createMapFromToken(String token);

    FormFieldsDto getFormFieldsForPublishPaper(String processInstanceId);

    PublishPaperForm getProccessIdAndFormFields(ProcessInstance pi);

    void submitPublishForm(List<FormSubmissionDto> submitedFields, String processInstanceId);

    WriterBookCreate createBook(String token, BookCreate bookCreateBody);
}
