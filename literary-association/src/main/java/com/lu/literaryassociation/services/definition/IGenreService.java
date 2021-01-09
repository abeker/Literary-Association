package com.lu.literaryassociation.services.definition;

import com.lu.literaryassociation.entity.Genre;
import com.lu.literaryassociation.services.implementation.GeneralException;

import java.util.List;

public interface IGenreService {

    List<Genre> getAllGenres() throws GeneralException;

    Genre getGenreByName(String name) throws GeneralException;

}
