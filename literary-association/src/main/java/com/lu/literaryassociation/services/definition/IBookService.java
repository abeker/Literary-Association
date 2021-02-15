package com.lu.literaryassociation.services.definition;

import com.lu.literaryassociation.dto.response.BookResponse;

import java.util.List;
import java.util.UUID;

public interface IBookService {

    List<BookResponse> getAll();

    List<BookResponse> getBooksForReader(UUID fromString);
}
