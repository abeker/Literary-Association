package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.entity.Genre;
import com.lu.literaryassociation.repository.IGenreRepository;
import com.lu.literaryassociation.services.definition.IGenreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService implements IGenreService {

   private final IGenreRepository iGenreRepository;

    public GenreService(IGenreRepository iGenreRepository) {
        this.iGenreRepository = iGenreRepository;
    }


    @Override
    public List<Genre> getAllGenres() throws GeneralException {
        return iGenreRepository.findAll();
    }

    @Override
    public Genre getGenreByName(String name) throws GeneralException {
        return iGenreRepository.findByGenreName(name);
    }


}
