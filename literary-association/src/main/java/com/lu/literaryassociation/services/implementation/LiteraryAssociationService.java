package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.dto.request.LiteraryAssociationRequest;
import com.lu.literaryassociation.dto.request.ReaderPaymentRequestDTO;
import com.lu.literaryassociation.dto.response.LiteraryAssociationResponse;
import com.lu.literaryassociation.dto.response.ReaderPaymentRequestResponse;
import com.lu.literaryassociation.entity.*;
import com.lu.literaryassociation.repository.*;
import com.lu.literaryassociation.services.definition.ILiteraryAssociationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class LiteraryAssociationService implements ILiteraryAssociationService {

    private final ILiteraryAssociationRepository _literaryAssociationRepository;
    private final IAddressRepository _addressRepository;
    private final IMembershipRepository _membershipRepository;
    private final IBookRepository _bookRepository;
    private final IReaderRepository _readerRepository;
    private final IReaderPaymentRequestRepository _readerPaymentRequestRepository;

    public LiteraryAssociationService(ILiteraryAssociationRepository literaryAssociationRepository, IAddressRepository addressRepository, IMembershipRepository membershipRepository, IBookRepository bookRepository, IReaderRepository readerRepository, IReaderPaymentRequestRepository readerPaymentRequestRepository) {
        _literaryAssociationRepository = literaryAssociationRepository;
        _addressRepository = addressRepository;
        _membershipRepository = membershipRepository;
        _bookRepository = bookRepository;
        _readerRepository = readerRepository;
        _readerPaymentRequestRepository = readerPaymentRequestRepository;
    }

    @Override
    public LiteraryAssociationResponse createLA(LiteraryAssociationRequest request) {
        LiteraryAssociation literaryAssociation = new LiteraryAssociation();
        literaryAssociation.setName(request.getName());
        literaryAssociation.setMembership(createMembership(request));
        literaryAssociation.setAddress(createAddress(request));
        return mapLiteraryAssociationToResponse(_literaryAssociationRepository.save(literaryAssociation));
    }

    @Override
    public ReaderPaymentRequestResponse createReaderPaymentRequest(ReaderPaymentRequestDTO request) {
        ReaderPaymentRequest newReaderPaymentRequest = new ReaderPaymentRequest();
        newReaderPaymentRequest.setBankCode(request.getBankCode());
        newReaderPaymentRequest.setPaymentCounter(request.getPaymentCounter());
        newReaderPaymentRequest.setReader(getReaderFromId(UUID.fromString(request.getReaderId())));
        newReaderPaymentRequest.setBook(getBookFromId(UUID.fromString(request.getBookId())));
        _readerPaymentRequestRepository.save(newReaderPaymentRequest);
        return mapReaderPaymentRequestToResponse(newReaderPaymentRequest);
    }

    private Reader getReaderFromId(UUID readerId) {
        Optional<Reader> reader = _readerRepository.findById(readerId);
        return reader.orElse(null);
    }

    private Book getBookFromId(UUID bookId) {
        Optional<Book> book = _bookRepository.findById(bookId);
        return book.orElse(null);
    }

    private ReaderPaymentRequestResponse mapReaderPaymentRequestToResponse(ReaderPaymentRequest newReaderPaymentRequest) {
        ReaderPaymentRequestResponse response = new ReaderPaymentRequestResponse();
        response.setId(newReaderPaymentRequest.getId().toString());
        return response;
    }

    private Address createAddress(LiteraryAssociationRequest request) {
        Address address = new Address();
        address.setCity(request.getCity());
        address.setCountry(request.getCountry());
        address.setStreetNumber(request.getStreetNumber());
        address.setZipCode(request.getZipCode());
        return _addressRepository.save(address);
    }

    private Membership createMembership(LiteraryAssociationRequest request) {
        Membership membership = new Membership();
        membership.setAmount(request.getMembershipAmount());
        membership.setDateOpened(LocalDate.now());
        return _membershipRepository.save(membership);
    }

    private LiteraryAssociationResponse mapLiteraryAssociationToResponse(LiteraryAssociation literaryAssociation) {
        LiteraryAssociationResponse response = new LiteraryAssociationResponse();
        response.setLuId(literaryAssociation.getId().toString());
        return response;
    }

}
