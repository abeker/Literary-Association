package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.dto.response.BookRequestDTO;
import com.lu.literaryassociation.dto.response.HandwriteDto;
import com.lu.literaryassociation.entity.*;
import com.lu.literaryassociation.repository.IBookRepository;
import com.lu.literaryassociation.repository.IBookRequestRepository;
import com.lu.literaryassociation.repository.IHandwriteRepository;
import com.lu.literaryassociation.services.definition.IBookRequestService;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookRequestService implements IBookRequestService {

    private final IBookRequestRepository _bookRequestRepository;
    private final IBookRepository _bookRepository;
    private final RuntimeService _runtimeService;
    private final IHandwriteRepository _handwriteRepository;

    public BookRequestService(IBookRequestRepository bookRequestRepository, IBookRepository bookRepository, RuntimeService runtimeService, IHandwriteRepository handwriteRepository) {
        _bookRequestRepository = bookRequestRepository;
        _bookRepository = bookRepository;
        _runtimeService = runtimeService;
        _handwriteRepository = handwriteRepository;
    }

    @Override
    public List<BookRequestDTO> getAll() {
        List<BookRequestDTO> retBookRequests = new ArrayList<>();
        for (BookRequest bookRequest : _bookRequestRepository.findAll()) {
            if(!isCreatedBookFromBookRequest(bookRequest)) {
                retBookRequests.add(mapBookRequestToDTO(bookRequest));
            }
        }
        return retBookRequests;
    }

    @Override
    public BookRequestDTO getBookRequestFromProcess(String processInstanceId) {
        BookRequest bookRequest = getBookRequestFromId(processInstanceId);
        if(bookRequest != null) {
            return mapBookRequestToDTO(bookRequest);
        }
        return null;
    }

    @Override
    public HandwriteDto getHandwriteFromProccess(String processInstanceId) {
        BookRequest bookRequest = getBookRequestFromId(processInstanceId);
        Handwrite handwrite = getHandwriteForBookRequest(bookRequest);
        if(bookRequest != null && handwrite != null) {
            HandwriteDto handwriteDto = new HandwriteDto();
            handwriteDto.setBookRequestDTO(mapBookRequestToDTO(bookRequest));
            handwriteDto.setHandwriteFileName(handwrite.getHandwriteFileName());
            handwriteDto.setId(handwrite.getId().toString());
            return handwriteDto;
        }
        return null;
    }

    private Handwrite getHandwriteForBookRequest(BookRequest bookRequest) {
        for (Handwrite handwrite : _handwriteRepository.findAll()) {
            if(handwrite.getBookRequest().getId().equals(bookRequest.getId())) {
                return handwrite;
            }
        }
        return null;
    }

    private BookRequest getBookRequestFromId(String processInstanceId) {
        String requestBookId = (String) _runtimeService.getVariable(processInstanceId, "requestBookId");
        Optional<BookRequest> bookRequestOptional = _bookRequestRepository.findById(UUID.fromString(requestBookId));
        return bookRequestOptional.orElse(null);
    }

    private boolean isCreatedBookFromBookRequest(BookRequest bookRequest) {
        for (Book book : _bookRepository.findAll()) {
            if(book.getBookRequest().getId().equals(bookRequest.getId())) {
                return true;
            }
        }
        return false;
    }

    private BookRequestDTO mapBookRequestToDTO(BookRequest bookRequest) {
        BookRequestDTO bookRequestDTO = new BookRequestDTO();
        bookRequestDTO.setId(bookRequest.getId().toString());
        bookRequestDTO.setGenres(getGenreNamesToString(bookRequest.getGenre()));
        bookRequestDTO.setSynopsis(bookRequest.getSynopsis());
        bookRequestDTO.setTitle(bookRequest.getTitle());
        bookRequestDTO.setWriters(getWriterNamesToString(bookRequest.getWriters()));
        bookRequestDTO.setApproved(bookRequest.isApproved());
        return bookRequestDTO;
    }

    private List<String> getWriterNamesToString(Set<Writer> writers) {
        List<String> retList = new ArrayList<>();
        for (Writer writer : writers) {
            retList.add(writer.getFirstName() + " " + writer.getLastName());
        }
        return retList;
    }

    private List<String> getGenreNamesToString(Set<Genre> genreList) {
        List<String> retList = new ArrayList<>();
        for (Genre genre : genreList) {
            retList.add(genre.getGenreName());
        }
        return retList;
    }
}
