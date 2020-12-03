package com.lu.literaryassociation.services.implementation;

import com.lu.literaryassociation.entity.User;
import com.lu.literaryassociation.entity.UserDetailsFactory;
import com.lu.literaryassociation.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserRepository _userRepository;

    @Autowired
    public UserDetailsServiceImpl(IUserRepository userRepository) {
        this._userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        final User user = _userRepository.findOneByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }
        return UserDetailsFactory.create(user);
    }

}
