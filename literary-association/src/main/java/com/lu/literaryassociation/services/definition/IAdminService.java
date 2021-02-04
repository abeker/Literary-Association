package com.lu.literaryassociation.services.definition;

import com.lu.literaryassociation.dto.response.DeletedUser;

import java.util.List;

public interface IAdminService {

    List<DeletedUser> deleteInactiveUsers(int daysInactive);

}
