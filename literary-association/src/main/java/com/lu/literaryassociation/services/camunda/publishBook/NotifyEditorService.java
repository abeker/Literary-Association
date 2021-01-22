package com.lu.literaryassociation.services.camunda.publishBook;

import com.lu.literaryassociation.entity.BookRequest;
import com.lu.literaryassociation.entity.Editor;
import com.lu.literaryassociation.entity.Genre;
import com.lu.literaryassociation.repository.IBookRequestRepository;
import com.lu.literaryassociation.repository.IEditorRepository;
import com.lu.literaryassociation.services.definition.IEmailService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("UnnecessaryLocalVariable")
@Service
public class NotifyEditorService implements JavaDelegate {

    private final IEmailService _emailService;
    private final RuntimeService _runtimeService;
    private final IEditorRepository _editorRepository;
    private final IBookRequestRepository _bookRequestRepository;

    public NotifyEditorService(IEmailService emailService, RuntimeService runtimeService, IEditorRepository editorRepository, IBookRequestRepository bookRequestRepository) {
        _emailService = emailService;
        _runtimeService = runtimeService;
        _editorRepository = editorRepository;
        _bookRequestRepository = bookRequestRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) {
        Editor mainEditor = getMainEditor();
        _runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "mainEditor", mainEditor.getUsername());
        BookRequest bookRequest = getBookRequestFromId(delegateExecution);
        if(bookRequest != null) {
            String genres = getGenresInStringType(bookRequest.getGenre());
            _emailService.sendNotificationToEditor(mainEditor, "New Publish Request", bookRequest.getTitle(), genres, bookRequest.getSynopsis());
        }
    }

    private BookRequest getBookRequestFromId(DelegateExecution delegateExecution) {
        String requestBookId = (String) delegateExecution.getVariable("requestBookId");
        Optional<BookRequest> bookRequestOptional = _bookRequestRepository.findById(UUID.fromString(requestBookId));
        return bookRequestOptional.orElse(null);
    }

    private String getGenresInStringType(Set<Genre> genres) {
        StringBuilder retStringGenres = new StringBuilder();
        for (Genre genreOption : genres) {
            retStringGenres.append(genreOption.getGenreName()).append(", ");
        }
        if(!retStringGenres.toString().equals("")) {
            retStringGenres = new StringBuilder(retStringGenres.substring(0, retStringGenres.length() - 2));
        }

        return retStringGenres.toString();
    }

    private Editor getMainEditor() {
        Editor majorEditor = _editorRepository.findAll()
                .stream()
                .filter(editor -> !editor.isDeleted() && editor.isMajor())
                .collect(Collectors.toList())
                .get(0);
        return majorEditor;
    }
}
