package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.dto.request.BookCreate;
import com.lu.literaryassociation.dto.request.FormFieldsDto;
import com.lu.literaryassociation.dto.request.FormSubmissionDto;
import com.lu.literaryassociation.dto.response.PublishPaperForm;
import com.lu.literaryassociation.dto.response.WriterBookCreate;
import com.lu.literaryassociation.entity.*;
import com.lu.literaryassociation.repository.*;
import com.lu.literaryassociation.security.TokenUtils;
import com.lu.literaryassociation.services.definition.IGenreService;
import com.lu.literaryassociation.services.definition.IWriterService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@SuppressWarnings("UnusedReturnValue")
@Service
public class WriterService implements IWriterService {

    private final TokenUtils _tokenUtils;
    private final TaskService _taskService;
    private final FormService _formService;
    private final IGenreService _genreService;
    private final IGenreRepository _genreRepository;
    private final IBookRequestRepository _bookRequestRepository;
    private final IWriterRepository _writerRepository;
    private final IUserRepository _userRepository;
    private final RuntimeService _runtimeService;
    private final IBookRepository _bookRepository;

    public WriterService(TokenUtils tokenUtils, TaskService taskService, FormService formService, IGenreService genreService, IGenreRepository genreRepository, IBookRequestRepository bookRequestRepository, IWriterRepository writerRepository, IUserRepository userRepository, RuntimeService runtimeService, IBookRepository bookRepository) {
        _tokenUtils = tokenUtils;
        _taskService = taskService;
        _formService = formService;
        _genreService = genreService;
        _genreRepository = genreRepository;
        _bookRequestRepository = bookRequestRepository;
        _writerRepository = writerRepository;
        _userRepository = userRepository;
        _runtimeService = runtimeService;
        _bookRepository = bookRepository;
    }

    @Override
    public Map<String, Object> createMapFromToken(String token) {
        String username = _tokenUtils.getUsernameFromToken(token);
        Map<String, Object> variableMap = new HashMap<>();
        variableMap.put("writerUsername", username);
        return variableMap;
    }

    @Override
    public FormFieldsDto getFormFieldsForPublishPaper(String processInstanceId) {
        System.out.println("PROCES: " + processInstanceId);
        Task task = _taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);

        TaskFormData tfd = _formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for(FormField fp : properties) {
            if(fp.getId().equals("genre")) {
                fp.getProperties().put("values", getAllGenres());
            }
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFieldsDto(task.getId(),processInstanceId, properties);
    }

    @Override
    public PublishPaperForm getProccessIdAndFormFields(ProcessInstance pi) {
        PublishPaperForm retProccessIdAndFormFields = new PublishPaperForm();
        retProccessIdAndFormFields.setProcessInstanceId(pi.getId());
        retProccessIdAndFormFields.setFormFieldsDto(getFormFieldsForPublishPaper(pi.getId()));
        return retProccessIdAndFormFields;
    }

    @Override
    public void submitPublishForm(List<FormSubmissionDto> submitedFields, String processInstanceId) {
        BookRequest bookRequest = createBookRequest(submitedFields, processInstanceId);
        HashMap<String, Object> map = this.mapListToDto(submitedFields);

        Task task = _taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);
        _runtimeService.setVariable(processInstanceId, "requestBookId", bookRequest.getId().toString());
        _formService.submitTaskForm(task.getId(), map);
    }

    @Override
    public WriterBookCreate createBook(String token, BookCreate bookCreateBody) {
        String username = _tokenUtils.getUsernameFromToken(token);
        BookRequest bookRequest = createBookRequest(username, bookCreateBody);
        Book book = createBook(bookRequest, bookCreateBody);
        return mapBookToWriterBookCreate(book);
    }

    private WriterBookCreate mapBookToWriterBookCreate(Book book) {
        return WriterBookCreate.builder()
                .id(book.getId().toString())
                .build();
    }

    private Book createBook(BookRequest bookRequest, BookCreate bookCreateBody) {
        Book book = new Book();
        book.setBookRequest(bookRequest);
        book.setISBN("978-3-16-456890-1");
        book.setNumberOfPages(bookCreateBody.getNumberOfPages());
        book.setPublishPlace("Novi Sad");
        book.setPublishYear(LocalDate.now());
        book.setRecommendedPrice(bookCreateBody.getPrice());
        return _bookRepository.save(book);
    }

    private BookRequest createBookRequest(String username, BookCreate bookCreateBody) {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setApproved(true);
        Set<Writer> writers = new HashSet<>();
        writers.add(_writerRepository.findOneByUsername(username));
        bookRequest.setWriters(writers);
        bookRequest.setSynopsis(bookCreateBody.getSynopsis());
        bookRequest.setGenre(getGenresFromIds(bookCreateBody.getGenreIds()));
        bookRequest.setTitle(bookCreateBody.getTitle());
        return _bookRequestRepository.save(bookRequest);
    }

    private Set<Genre> getGenresFromIds(List<String> genreIds) {
        Set<Genre> retGenres = new HashSet<>();
        for (String genreId : genreIds) {
            Optional<Genre> genreOptional = _genreRepository.findById(UUID.fromString(genreId));
            if(genreOptional.isPresent()) {
                retGenres.add(genreOptional.get());
            }
        }
        return retGenres;
    }

    private BookRequest createBookRequest(List<FormSubmissionDto> submitedFields, String processInstanceId) {
        BookRequest bookRequest = new BookRequest();
        for (FormSubmissionDto formField : submitedFields) {
            switch (formField.getFieldId()) {
                case "title": bookRequest.setTitle(formField.getFieldValue());
                break;
                case "genre":
                    _runtimeService.setVariable(processInstanceId, "bookRequestGenres", formField.getFieldValue());
                    bookRequest.setGenre(parseGenres(formField.getFieldValue()));
                    break;
                case "synopsis": bookRequest.setSynopsis(formField.getFieldValue());
            }
        }
        setWritersForBookRequest(processInstanceId, bookRequest);
        return _bookRequestRepository.save(bookRequest);
    }

    private void setWritersForBookRequest(String processInstanceId, BookRequest bookRequest) {
        String asigneeUsername = (String)_runtimeService.getVariable(processInstanceId, "writerUsername");
        Set<Writer> writersOfBook = new HashSet<>();
        Writer writer = getWriterFromUsername(asigneeUsername);
        writersOfBook.add(writer);
        bookRequest.getWriters().add(writer);
    }

    private Writer getWriterFromUsername(String username) {
        User user = _userRepository.findOneByUsername(username);
        if(user != null) {
            if(!user.isDeleted()) {
                Optional<Writer> retWriter = _writerRepository.findById(user.getId()).filter(writer -> !writer.isDeleted());
                return retWriter.get();
            }
        }
        return null;
    }

    private Set<Genre> parseGenres(String genres) {
        String[] genreList = genres.split(";");
        Set<Genre> retGenres = new HashSet<>();
        for (String genreStringFromList : genreList) {
            Genre genre = _genreRepository.findByGenreName(genreStringFromList.trim());
            retGenres.add(genre);
        }
        return retGenres;
    }

    private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormSubmissionDto temp : list){
            map.put(temp.getFieldId(), temp.getFieldValue());
        }
        return map;
    }

    private String getAllGenres() {
        String genres="";
        List<Genre> genreList = _genreService.getAllGenres();
        for (Genre g: genreList) {
            //genres=romance;horror
            genres = genres.concat(g.getGenreName().concat(";"));
        }
        return genres;
    }
}
