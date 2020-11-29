package com.lu.literaryassociation.service;

import com.lu.literaryassociation.dto.request.LoginRequest;
import com.lu.literaryassociation.dto.response.LoginResponse;
import com.lu.literaryassociation.service.implementation.GeneralException;

/**
 *  USE GENERALEXCEPTION INSTEAD OF EXCEPTION
 *     GeneralException extends RuntimeException
 *     usage example:  throw new GeneralException("Wrong username", HttpStatus.BAD_REQUEST)
 *                                                 message_for_user    http_status
 */

public interface IAuthService {

    LoginResponse login(LoginRequest request) throws GeneralException;

}
