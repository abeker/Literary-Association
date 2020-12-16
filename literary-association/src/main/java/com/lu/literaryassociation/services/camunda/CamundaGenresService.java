package com.lu.literaryassociation.services.camunda;

import com.lu.literaryassociation.entity.Genre;
import com.lu.literaryassociation.services.definition.IGenreService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CamundaGenresService implements JavaDelegate {

    private final IGenreService iGenreService;

    public CamundaGenresService(IGenreService iGenreService) {
        this.iGenreService = iGenreService;
    }


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String genres="";
        List<Genre> genreList = iGenreService.getAllGenres();
        for (Genre g: genreList) {
            //genres=romance;horror
            genres = genres.concat(g.getGenreName().concat(";"));
        }
        delegateExecution.setVariable("genres", genres);
        System.out.println(delegateExecution.getVariable("genres"));

    }
}
