package com.lu.literaryassociation.services.camunda.publishBook;

import com.lu.literaryassociation.entity.BookRequest;
import com.lu.literaryassociation.entity.Handwrite;
import com.lu.literaryassociation.repository.IBookRequestRepository;
import com.lu.literaryassociation.repository.IHandwriteRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CreateHandwriteService implements JavaDelegate {

    private final IHandwriteRepository _handwritingRepository;
    private final IBookRequestRepository _bookRequestRepository;

    public CreateHandwriteService(IHandwriteRepository handwritingRepository, IBookRequestRepository bookRequestRepository) {
        _handwritingRepository = handwritingRepository;
        _bookRequestRepository = bookRequestRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) {
        String handwriteFileName = (String)delegateExecution.getVariable("handwriteFileName");
        BookRequest bookRequest = getBookRequestFromId(delegateExecution);
        createHandwrite(handwriteFileName, bookRequest);
    }

    private void createHandwrite(String handwriteFileName, BookRequest bookRequest) {
        Handwrite handwrite = new Handwrite();
        handwrite.setBookRequest(bookRequest);
        handwrite.setHandwriteFileName(handwriteFileName);
        _handwritingRepository.save(handwrite);
    }

    private BookRequest getBookRequestFromId(DelegateExecution delegateExecution) {
        String requestBookId = (String) delegateExecution.getVariable("requestBookId");
        Optional<BookRequest> bookRequestOptional = _bookRequestRepository.findById(UUID.fromString(requestBookId));
        return bookRequestOptional.orElse(null);
    }

}
