package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.dto.response.GenreResponse;
import com.lu.literaryassociation.entity.Genre;
import com.lu.literaryassociation.repository.IGenreRepository;
import com.lu.literaryassociation.services.definition.IGenreService;
import com.lu.literaryassociation.util.exceptions.GeneralException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService implements IGenreService {

   private final IGenreRepository iGenreRepository;

    public GenreService(IGenreRepository iGenreRepository) {
        this.iGenreRepository = iGenreRepository;
    }


    @Override
    public List<Genre> getAllGenres() throws GeneralException {
        return iGenreRepository.findAll().stream()
                .filter(genre -> !genre.isDeleted())
                .collect(Collectors.toList());
    }

    @Override
    public Genre getGenreByName(String name) throws GeneralException {
        return iGenreRepository.findByGenreName(name);
    }

    @Override
    public List<GenreResponse> getGenres() {
        List<GenreResponse> retGenres = new ArrayList<>();
        for (Genre genre : getAllGenres()) {
            retGenres.add(mapGenreToResponse(genre));
        }
        return retGenres;
    }

    private GenreResponse mapGenreToResponse(Genre genre) {
        return GenreResponse.builder()
                .code(genre.getCode())
                .id(genre.getId().toString())
                .name(genre.getGenreName())
                .build();
    }

}
