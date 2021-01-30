package com.lu.literaryassociation.dto.response;

import lombok.Data;

@Data
public class HandwriteDto {

    private String id;
    private String handwriteFileName;
    private BookRequestDTO bookRequestDTO;

}
