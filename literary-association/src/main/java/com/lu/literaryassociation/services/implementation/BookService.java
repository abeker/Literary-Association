package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.dto.response.BookResponse;
import com.lu.literaryassociation.entity.Book;
import com.lu.literaryassociation.entity.ReaderPaymentRequest;
import com.lu.literaryassociation.repository.IBookRepository;
import com.lu.literaryassociation.repository.IReaderPaymentRequestRepository;
import com.lu.literaryassociation.services.definition.IBookRequestService;
import com.lu.literaryassociation.services.definition.IBookService;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookService implements IBookService {

    private final IBookRepository _bookRepository;
    private final IBookRequestService _bookRequestService;
    private final IReaderPaymentRequestRepository _readerPaymentRequestRepository;

    public BookService(IBookRepository bookRepository, IBookRequestService bookRequestService, IReaderPaymentRequestRepository readerPaymentRequestRepository) {
        _bookRepository = bookRepository;
        _bookRequestService = bookRequestService;
        _readerPaymentRequestRepository = readerPaymentRequestRepository;
    }

    @Override
    public List<BookResponse> getAll() {
        List<Book> activeBooks = _bookRepository.findAll()
                .stream()
                .filter(book -> !book.isDeleted())
                .collect(Collectors.toList());

        List<BookResponse> retBookList = new ArrayList<>();
        for (Book book : activeBooks) {
            retBookList.add(mapBookToBookResponse(book));
        }
        return retBookList;
    }

    @Override
    public List<BookResponse> getBooksForReader(UUID readerId) {
        List<ReaderPaymentRequest> paymentRequestsForReader = _readerPaymentRequestRepository.findAll()
                .stream()
                .filter(request -> request.getReader().getId().equals(readerId))
                .collect(Collectors.toList());

        List<BookResponse> retBookList = new ArrayList<>();
        for (ReaderPaymentRequest request : paymentRequestsForReader) {
            retBookList.add(mapBookToBookResponse(request.getBook()));
        }
        return retBookList;
    }

    private BookResponse mapBookToBookResponse(Book book) {
        BookResponse bookResponse = new BookResponse();
        bookResponse.setId(book.getId().toString());
        bookResponse.setISBN(book.getISBN());
        bookResponse.setNumberOfPages(book.getNumberOfPages());
        bookResponse.setPublishPlace(book.getPublishPlace());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        bookResponse.setPublishYear(book.getPublishYear().format(formatter));
        bookResponse.setBookRequest(_bookRequestService.mapBookRequestToDTO(book.getBookRequest()));
        bookResponse.setPrice(book.getRecommendedPrice());
        return bookResponse;
    }

}
