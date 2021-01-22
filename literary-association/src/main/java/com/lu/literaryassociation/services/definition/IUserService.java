package com.lu.literaryassociation.services.definition;

import com.lu.literaryassociation.dto.response.UserStored;
import com.lu.literaryassociation.entity.User;
import com.lu.literaryassociation.util.exceptions.GeneralException;

import java.util.UUID;

public interface IUserService {

    UserStored getUser(String username) throws GeneralException;

    User getUserById(UUID uuid);

    User findUser(String username);

}
