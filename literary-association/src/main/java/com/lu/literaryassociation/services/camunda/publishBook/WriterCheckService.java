package com.lu.literaryassociation.services.camunda.publishBook;

import com.lu.literaryassociation.entity.User;
import com.lu.literaryassociation.repository.IUserRepository;
import com.lu.literaryassociation.util.enums.UserType;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class WriterCheckService implements JavaDelegate {

    private final IUserRepository _userRepository;

    public WriterCheckService(IUserRepository userRepository) {
        _userRepository = userRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) {
        String writerUsername = (String)delegateExecution.getVariable("writerUsername");
        boolean isRegisteredWriter = checkWriterRegistration(writerUsername);
        delegateExecution.setVariable("isRegisteredWriter", isRegisteredWriter);
    }

    private boolean checkWriterRegistration(String username) {
        User user = getUserFromUsername(username);
        if(user != null) {
            return user.getUserType().equals(UserType.WRITER);
        }
        return false;
    }

    private User getUserFromUsername(String username) {
        User user = _userRepository.findOneByUsername(username);
        if(user != null) {
            if(user.isApproved() && !user.isDeleted()) {
                return user;
            }
        }
        return null;
    }


}
