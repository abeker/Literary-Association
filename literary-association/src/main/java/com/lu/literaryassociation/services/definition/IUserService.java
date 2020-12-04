package com.lu.literaryassociation.services.definition;

import com.lu.literaryassociation.dto.response.UserStored;
import com.lu.literaryassociation.services.implementation.GeneralException;

public interface IUserService {

    UserStored getUser(String username) throws GeneralException;

}
