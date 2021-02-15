package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.dto.request.LiteraryAssociationRequest;
import com.lu.literaryassociation.dto.request.ReaderPaymentRequestDTO;
import com.lu.literaryassociation.dto.response.LiteraryAssResponse;
import com.lu.literaryassociation.dto.response.LiteraryAssociationResponse;
import com.lu.literaryassociation.dto.response.LuSecret;
import com.lu.literaryassociation.dto.response.ReaderPaymentRequestResponse;
import com.lu.literaryassociation.entity.*;
import com.lu.literaryassociation.repository.*;
import com.lu.literaryassociation.security.TokenUtils;
import com.lu.literaryassociation.services.definition.ILiteraryAssociationService;
import com.lu.literaryassociation.services.definition.IUserMembershipService;
import com.lu.literaryassociation.util.enums.PaymentRequestStatus;
import com.lu.literaryassociation.util.exceptions.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class LiteraryAssociationService implements ILiteraryAssociationService {

    private final ILiteraryAssociationRepository _literaryAssociationRepository;
    private final IAddressRepository _addressRepository;
    private final IMembershipRepository _membershipRepository;
    private final IBookRepository _bookRepository;
    private final IReaderRepository _readerRepository;
    private final IUserRepository _userRepository;
    private final IAuthorityRepository _authorityRepository;
    private final IReaderPaymentRequestRepository _readerPaymentRequestRepository;
    private final IUserMembershipService _userMembershipService;
    private final TokenUtils _tokenUtils;
    private final PasswordEncoder _passwordEncoder;

    public LiteraryAssociationService(ILiteraryAssociationRepository literaryAssociationRepository, IAddressRepository addressRepository, IMembershipRepository membershipRepository, IBookRepository bookRepository, IReaderRepository readerRepository, IUserRepository userRepository, IAuthorityRepository authorityRepository, IReaderPaymentRequestRepository readerPaymentRequestRepository, IUserMembershipService userMembershipService, TokenUtils tokenUtils, PasswordEncoder passwordEncoder) {
        _literaryAssociationRepository = literaryAssociationRepository;
        _addressRepository = addressRepository;
        _membershipRepository = membershipRepository;
        _bookRepository = bookRepository;
        _readerRepository = readerRepository;
        _userRepository = userRepository;
        _authorityRepository = authorityRepository;
        _readerPaymentRequestRepository = readerPaymentRequestRepository;
        _userMembershipService = userMembershipService;
        _tokenUtils = tokenUtils;
        _passwordEncoder = passwordEncoder;
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
        boolean isUserPaidMembership = _userMembershipService.isUserPaidMembership(UUID.fromString(request.getReaderId()), 30);
        if(!isUserPaidMembership) {
            throw new GeneralException("You haven't paid your membership.", HttpStatus.CONFLICT);
        }
        ReaderPaymentRequest newReaderPaymentRequest = new ReaderPaymentRequest();
        newReaderPaymentRequest.setBankCode(request.getBankCode());
        newReaderPaymentRequest.setPaymentCounter(request.getPaymentCounter());
        newReaderPaymentRequest.setReader(getReaderFromId(UUID.fromString(request.getReaderId())));
        ReaderPaymentRequest savedReaderPaymentRequest = createBookPayment(newReaderPaymentRequest, request);
        User user = _userRepository.findOneById(UUID.fromString(request.getReaderId()));
        addRole(user, "ROLE_ADVANCED_READER");
        return mapReaderPaymentRequestToResponse(savedReaderPaymentRequest);
    }

    @Override
    public void addRole(User user, String role_name) {
        Set<Authority> userRoles = user.getRoles();
        userRoles.add(_authorityRepository.findByName(role_name));
        _userRepository.save(user);
    }

    @Override
    public void changeReaderPaymentStatus(UUID readerPaymentId, String status) {
        Optional<ReaderPaymentRequest> readerPaymentRequestOptional = _readerPaymentRequestRepository.findById(readerPaymentId);
        if(readerPaymentRequestOptional.isPresent()) {
            ReaderPaymentRequest readerPaymentRequest = readerPaymentRequestOptional.get();
            readerPaymentRequest.setStatus(getPaymentRequestStatusFromString(status.toUpperCase()));
            _readerPaymentRequestRepository.save(readerPaymentRequest);
        }
    }

    @Override
    public LuSecret getSecret(String token) {
        String username = _tokenUtils.getUsernameFromToken(token);
        User user = _userRepository.findOneByUsername(username);

        return mapLiteraryAssociationToSecret(user.getLiteraryAssociation());
    }

    @Override
    public List<LiteraryAssResponse> getAll() {
        List<LiteraryAssResponse> retLiteraryAssociations = new ArrayList<>();
        for (LiteraryAssociation literaryAssociation : _literaryAssociationRepository.findAll()) {
            retLiteraryAssociations.add(mapLiteraryAssociationToLiteraryAss(literaryAssociation));
        }
        return retLiteraryAssociations;
    }

    private LiteraryAssResponse mapLiteraryAssociationToLiteraryAss(LiteraryAssociation literaryAssociation) {
        return LiteraryAssResponse.builder()
                .address(literaryAssociation.getAddress().getStreetNumber() + ", " + literaryAssociation.getAddress().getCity())
                .id(literaryAssociation.getId().toString())
                .name(literaryAssociation.getName())
                .build();
    }

    private LuSecret mapLiteraryAssociationToSecret(LiteraryAssociation literaryAssociation) {
        return LuSecret.builder()
                .secret(literaryAssociation.getSecret())
                .password(literaryAssociation.getPassword())
                .build();
    }

    private PaymentRequestStatus getPaymentRequestStatusFromString(String status) {
        switch (status) {
            case "FAIL": return PaymentRequestStatus.FAIL;
            case "ERROR": return PaymentRequestStatus.ERROR;
            case "SUCCESS": return PaymentRequestStatus.SUCCESS;
            default: return PaymentRequestStatus.PENDING;
        }
    }

    private ReaderPaymentRequest createBookPayment(ReaderPaymentRequest readerPaymentRequest, ReaderPaymentRequestDTO request) {
        for (String idAsString : request.getBookIds()) {
            Optional<Book> bookOptional = _bookRepository.findById(UUID.fromString(idAsString));
            if(bookOptional.isPresent()) {
                Book book = bookOptional.get();
                ReaderPaymentRequest newReaderPaymentRequest = createDeepCopyOfReaderPayment(readerPaymentRequest);
                newReaderPaymentRequest.setBook(book);
                return _readerPaymentRequestRepository.save(newReaderPaymentRequest);
            }
        }
        return null;
    }

    private ReaderPaymentRequest createDeepCopyOfReaderPayment(ReaderPaymentRequest request) {
        ReaderPaymentRequest readerPaymentRequest = new ReaderPaymentRequest();
        readerPaymentRequest.setReader(request.getReader());
        readerPaymentRequest.setBankCode(request.getBankCode());
        readerPaymentRequest.setPaymentCounter(request.getPaymentCounter());
        readerPaymentRequest.setStatus(request.getStatus());
        return readerPaymentRequest;
    }

    private Reader getReaderFromId(UUID readerId) {
        Optional<Reader> reader = _readerRepository.findById(readerId).filter(reader1 -> !reader1.isDeleted());
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
