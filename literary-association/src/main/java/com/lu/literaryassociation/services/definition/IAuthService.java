package com.lu.literaryassociation.services.definition;

import com.lu.literaryassociation.dto.request.LoginRequest;
import com.lu.literaryassociation.dto.response.UserResponse;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public interface IAuthService {

    UserResponse login(LoginRequest request, HttpServletRequest httpServletRequest) throws SQLException;

}
