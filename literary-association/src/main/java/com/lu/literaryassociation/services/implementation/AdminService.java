package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.dto.response.DeletedUser;
import com.lu.literaryassociation.entity.User;
import com.lu.literaryassociation.repository.IUserRepository;
import com.lu.literaryassociation.services.definition.IAdminService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService implements IAdminService {

    private final IUserRepository _userRepository;

    public AdminService(IUserRepository userRepository) {
        _userRepository = userRepository;
    }

    @Override
    public List<DeletedUser> deleteInactiveUsers(int daysInactive) {
        List<DeletedUser> retDeleteUsersList = new ArrayList<>();
        for (User user : getAllUsers()) {
            if(user.getLastTimeActive().plusDays(daysInactive).isBefore(LocalDateTime.now())) {
                user.setDeleted(true);
                retDeleteUsersList.add(mapUserToUserStored(user));
                _userRepository.save(user);
            }
        }
        return retDeleteUsersList;
    }

    private List<User> getAllUsers() {
        return _userRepository.findAll()
                .stream()
                .filter(user -> !user.isDeleted() && user.isApproved())
                .collect(Collectors.toList());
    }

    private DeletedUser mapUserToUserStored(User user) {
        return DeletedUser.builder()
                .id(user.getId().toString())
                .role(user.getUserType().toString())
                .username(user.getUsername())
                .build();
    }
}
