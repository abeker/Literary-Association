package com.lu.literaryassociation.services.definition;

import com.lu.literaryassociation.dto.response.GenreResponse;
import com.lu.literaryassociation.entity.Genre;
import com.lu.literaryassociation.util.exceptions.GeneralException;

import java.util.List;

public interface IGenreService {

    List<Genre> getAllGenres() throws GeneralException;

    Genre getGenreByName(String name) throws GeneralException;

    List<GenreResponse> getGenres();
}
