package com.bilitech.api.core.service.impl;

import com.bilitech.api.core.entity.User;
import com.bilitech.api.core.exception.BizException;
import com.bilitech.api.core.exception.ExceptionType;
import com.bilitech.api.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;


public abstract class BaseService {

    private UserRepository userRepository;


    protected boolean isUserExisted(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.isPresent();
    }

    protected User getUserById(String id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new BizException(ExceptionType.USER_NOT_FOUND);
        }
        return user.get();
    }


    protected User getCurrentUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return loadUserByUsername(authentication.getName());
    }

    protected User loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new BizException(ExceptionType.USER_NOT_FOUND);
        }
        return user.get();
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
