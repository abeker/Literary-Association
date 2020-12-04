package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.dto.response.UserStored;
import com.lu.literaryassociation.entity.User;
import com.lu.literaryassociation.repository.IUserRepository;
import com.lu.literaryassociation.services.definition.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final IUserRepository _userRepository;

    public UserService(IUserRepository userRepository) {
        _userRepository = userRepository;
    }

    @Override
    public UserStored getUser(String username) throws GeneralException {
        User user = _userRepository.findOneByUsername(username);
        return mapUserToUserStored(user);
    }

    private UserStored mapUserToUserStored(User user) {
        if(user != null) {
            return UserStored.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .city(user.getCity())
                    .country(user.getCountry()).build();
        }
        throw new GeneralException("User does not exist", HttpStatus.BAD_REQUEST);
    }

}
