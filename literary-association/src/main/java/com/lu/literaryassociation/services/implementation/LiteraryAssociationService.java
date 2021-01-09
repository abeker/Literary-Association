package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.dto.request.LiteraryAssociationRequest;
import com.lu.literaryassociation.dto.response.LiteraryAssociationResponse;
import com.lu.literaryassociation.entity.Address;
import com.lu.literaryassociation.entity.LiteraryAssociation;
import com.lu.literaryassociation.entity.Membership;
import com.lu.literaryassociation.repository.IAddressRepository;
import com.lu.literaryassociation.repository.ILiteraryAssociationRepository;
import com.lu.literaryassociation.repository.IMembershipRepository;
import com.lu.literaryassociation.services.definition.ILiteraryAssociationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LiteraryAssociationService implements ILiteraryAssociationService {

    private final ILiteraryAssociationRepository _literaryAssociationRepository;
    private final IAddressRepository _addressRepository;
    private final IMembershipRepository _membershipRepository;

    public LiteraryAssociationService(ILiteraryAssociationRepository literaryAssociationRepository, IAddressRepository addressRepository, IMembershipRepository membershipRepository) {
        _literaryAssociationRepository = literaryAssociationRepository;
        _addressRepository = addressRepository;
        _membershipRepository = membershipRepository;
    }

    @Override
    public LiteraryAssociationResponse createLA(LiteraryAssociationRequest request) {
        LiteraryAssociation literaryAssociation = new LiteraryAssociation();
        literaryAssociation.setName(request.getName());
        literaryAssociation.setMembership(createMembership(request));
        literaryAssociation.setAddress(createAddress(request));
        return mapLiteraryAssociationToResponse(_literaryAssociationRepository.save(literaryAssociation));
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
