package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.dto.request.ReaderRegistration;
import com.lu.literaryassociation.dto.response.CreatedReader;
import com.lu.literaryassociation.entity.BetaReader;
import com.lu.literaryassociation.entity.BetaReaderComment;
import com.lu.literaryassociation.entity.Genre;
import com.lu.literaryassociation.entity.Reader;
import com.lu.literaryassociation.repository.IBetaReaderCommentRepository;
import com.lu.literaryassociation.repository.IBetaReaderRepository;
import com.lu.literaryassociation.repository.IGenreRepository;
import com.lu.literaryassociation.repository.IReaderRepository;
import com.lu.literaryassociation.services.definition.IGenreService;
import com.lu.literaryassociation.services.definition.IReaderService;
import com.lu.literaryassociation.services.definition.IUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReaderService implements IReaderService {

    private final IReaderRepository iReaderRepository;
    private final IGenreRepository _genreRepository;
    private final IBetaReaderRepository iBetaReaderRepository;
    private final IBetaReaderCommentRepository iBetaReaderCommentRepository;
    private final IGenreService iGenreService;
    private final IUserService iUserService;

    public ReaderService(IReaderRepository iReaderRepository, IGenreRepository genreRepository, IBetaReaderRepository iBetaReaderRepository, IBetaReaderCommentRepository iBetaReaderCommentRepository, IGenreService iGenreService, IUserService iUserService) {
        this.iReaderRepository = iReaderRepository;
        _genreRepository = genreRepository;
        this.iBetaReaderRepository = iBetaReaderRepository;
        this.iBetaReaderCommentRepository = iBetaReaderCommentRepository;
        this.iGenreService = iGenreService;
        this.iUserService = iUserService;
    }

    @Override
    public CreatedReader registration(ReaderRegistration request) {
        return CreatedReader.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername()).build();
    }

    @Override
    public Reader findReaderByUsername(String username){
        return iReaderRepository.findOneByUsername(username);
    }


    @Override
    public List<String> findBetaReaderInfoByGenre(String genreName){
        //String = "USERNAME READERA: Ime Prezime"
        List<String> returnList = new ArrayList<>();
        Genre genre = iGenreService.getGenreByName(genreName);
        List<BetaReader> betaReaderList = iBetaReaderRepository.findByGenresId(genre.getId())
                .stream()
                .filter(betaReader -> !betaReader.getReader().isDeleted())
                .collect(Collectors.toList());
        for(BetaReader betaReader: betaReaderList ){
             Reader r = betaReader.getReader();
             String s = r.getUsername() + ":" + r.getFirstName() +" "+ r.getLastName();
             returnList.add(s);
        }
        return returnList;
    }


    @Override
    public boolean isBetaReader(String username){
         boolean flag = false;
         Reader r = iReaderRepository.findOneByUsername(username);
         if(r.getBetaReader() != null)
             flag = true;
         return flag;
    }


    @Override
    public BetaReaderComment saveBetaReaderComment(BetaReaderComment betaReaderComment){
       return iBetaReaderCommentRepository.save(betaReaderComment);
    }

    @Override
    public BetaReader updateBetaReader(BetaReader betaReader){
        return iBetaReaderRepository.save(betaReader);
    }


    @Override
    public List<BetaReaderComment> getBetaReaderCommentByProcess(String processId){
        return iBetaReaderCommentRepository.findByProcessInstance(processId);
    }

    @Override
    public List<String> findBetaReaderInfoByMultiGenre(String genreName) {
        List<String> retBetaReaders = new ArrayList<>();
        List<Genre> listOfGenres = getGenresFromString(genreName);
        List<BetaReader> listOfBetaReaders = getBetaReadersForGenres(listOfGenres);
        for (BetaReader betaReader : listOfBetaReaders) {
            retBetaReaders.add(mapBetaReaderToString(betaReader));
        }
        return retBetaReaders;
    }

    private String mapBetaReaderToString(BetaReader betaReader) {
        return betaReader.getReader().getUsername() + ":" + betaReader.getReader().getFirstName() + " " + betaReader.getReader().getLastName();
    }

    private List<BetaReader> getBetaReadersForGenres(List<Genre> listOfGenres) {
        List<BetaReader> retBetaReaders = new ArrayList<>();
        for (BetaReader betaReader : getAllBetaReaders()) {
            for (Genre genre : listOfGenres) {
                if(betaReader.getGenres().contains(genre)) {
                    retBetaReaders.add(betaReader);
                    break;
                }
            }
        }
        return retBetaReaders;
    }

    private List<BetaReader> getAllBetaReaders() {
        return iBetaReaderRepository.findAll()
                .stream()
                .filter(betaReader -> !betaReader.getReader().isDeleted())
                .collect(Collectors.toList());
    }

    private List<Genre> getGenresFromString(String genreName) {
        String[] genreList = genreName.split(";");
        List<Genre> retGenreList = new ArrayList<>();
        for (String genreString : genreList) {
            Genre genre = _genreRepository.findByGenreName(genreString.trim());
            if(genre != null) {
                retGenreList.add(genre);
            }
        }
        return retGenreList;
    }


}
