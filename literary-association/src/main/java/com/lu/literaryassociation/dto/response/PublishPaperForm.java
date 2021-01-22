package com.lu.literaryassociation.dto.response;

import com.lu.literaryassociation.dto.request.FormFieldsDto;
import lombok.Data;

@Data
public class PublishPaperForm {

    private String processInstanceId;
    private FormFieldsDto formFieldsDto;

}
