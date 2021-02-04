package com.lu.literaryassociation.controller;

import com.lu.literaryassociation.dto.response.BookResponse;
import com.lu.literaryassociation.services.definition.IBookService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookController {

    private final IBookService _bookService;

    public BookController(IBookService bookService) {
        _bookService = bookService;
    }

    @GetMapping(produces = "application/json")
    public List<BookResponse> getAll() {
        return _bookService.getAll();
    }

    @GetMapping(path = "/{readerId}", produces = "application/json")
    @PreAuthorize("hasAuthority('RETRIEVE_PURCHASED_BOOKS')")
    public List<BookResponse> getBooksForReader(@PathVariable("readerId") String readerId) {
        return _bookService.getBooksForReader(UUID.fromString(readerId));
    }

}
