package com.lu.literaryassociation.controller;

import com.lu.literaryassociation.dto.request.LiteraryAssociationRequest;
import com.lu.literaryassociation.dto.request.ReaderPaymentRequestDTO;
import com.lu.literaryassociation.dto.response.LiteraryAssociationResponse;
import com.lu.literaryassociation.dto.response.ReaderPaymentRequestResponse;
import com.lu.literaryassociation.services.definition.ILiteraryAssociationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/la")
public class LiteraryAssociationController {

    private final ILiteraryAssociationService _literaryAssociationService;

    public LiteraryAssociationController(ILiteraryAssociationService literaryAssociationService) {
        _literaryAssociationService = literaryAssociationService;
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('CREATE_LITERARY_ASSOCIATION')")
    public LiteraryAssociationResponse createLA(@RequestBody LiteraryAssociationRequest request) {
        return _literaryAssociationService.createLA(request);
    }

    @PostMapping("/reader-pay-request")
    @PreAuthorize("hasAuthority('PURCHASE_BOOK')")
    public ReaderPaymentRequestResponse createReaderPaymentRequest(@RequestBody ReaderPaymentRequestDTO request) {
        return _literaryAssociationService.createReaderPaymentRequest(request);
    }

    @PutMapping("/reader-pay/{readerPaymentId}/status/{status}")
    @PreAuthorize("hasAuthority('PURCHASE_BOOK')")
    public void changeReaderPaymentStatus(@PathVariable("readerPaymentId") String readerPaymentId, @PathVariable("status") String status) {
        _literaryAssociationService.changeReaderPaymentStatus(UUID.fromString(readerPaymentId), status);
    }

}
