package com.lu.literaryassociation.controller;

import com.lu.literaryassociation.dto.response.GenreResponse;
import com.lu.literaryassociation.services.definition.IGenreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private final IGenreService _genreService;

    public GenreController(IGenreService genreService) {
        _genreService = genreService;
    }

    @GetMapping(produces = "application/json")
    public List<GenreResponse> getAll() {
        return _genreService.getGenres();
    }

}
