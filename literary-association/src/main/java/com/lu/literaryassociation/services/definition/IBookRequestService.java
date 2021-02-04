package com.lu.literaryassociation.services.definition;

import com.lu.literaryassociation.dto.response.BookRequestDTO;
import com.lu.literaryassociation.dto.response.HandwriteDto;

import java.util.List;

public interface IBookRequestService {

    List<BookRequestDTO> getAll();

    BookRequestDTO getBookRequestFromProcess(String processInstanceId);

    HandwriteDto getHandwriteFromProccess(String processInstanceId);

    boolean canChangeHandwrite(String processInstanceId);
}
