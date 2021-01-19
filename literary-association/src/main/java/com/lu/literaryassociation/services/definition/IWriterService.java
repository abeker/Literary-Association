package com.lu.literaryassociation.services.definition;

import com.lu.literaryassociation.dto.request.FormFieldsDto;

import java.util.Map;

public interface IWriterService {
    Map<String, Object> createMapFromToken(String token);

    FormFieldsDto getFormFieldsForPublishPaper(String processInstanceId);
}
