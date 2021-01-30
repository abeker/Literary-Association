package com.lu.literaryassociation.services.camunda.publishBook;

import com.lu.literaryassociation.entity.BookRequest;
import com.lu.literaryassociation.entity.Writer;
import com.lu.literaryassociation.repository.IBookRequestRepository;
import com.lu.literaryassociation.services.definition.IEmailService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("DuplicatedCode")
@Service
public class NotifyWriterToSendHandwrite implements JavaDelegate {

    private final IEmailService _emailService;
    private final IBookRequestRepository _bookRequestRepository;

    public NotifyWriterToSendHandwrite(IEmailService emailService, IBookRequestRepository bookRequestRepository) {
        _emailService = emailService;
        _bookRequestRepository = bookRequestRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) {
        BookRequest bookRequest = getBookRequest(delegateExecution);
        if(bookRequest != null) {
            _emailService.sendHandwrite(getWriterFromBookRequest(bookRequest), "Handwrite Submission", bookRequest.getTitle(), bookRequest.getSynopsis());
        }
    }

    private Writer getWriterFromBookRequest(BookRequest bookRequest) {
        for (Writer writer : bookRequest.getWriters()) {
            return writer;
        }
        return null;
    }

    private BookRequest getBookRequest(DelegateExecution delegateExecution) {
        String requestBookId = (String) delegateExecution.getVariable("requestBookId");
        Optional<BookRequest> bookRequestOptional = _bookRequestRepository.findById(UUID.fromString(requestBookId));
        return bookRequestOptional.orElse(null);
    }

}
