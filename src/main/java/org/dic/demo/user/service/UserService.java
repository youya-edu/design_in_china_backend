package org.dic.demo.user.service;

import org.apache.commons.lang3.StringUtils;
import org.dic.demo.user.exception.LoginInfoNotEnoughException;
import org.dic.demo.user.exception.UserNotAuthenticatedException;
import org.dic.demo.user.exception.UserNotFoundException;
import org.dic.demo.user.model.User;
import org.dic.demo.user.repository.UserRepository;
import org.dic.demo.user.model.LoginInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(long userId) {
        return userRepository.getUserById(userId);
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public User authenticateUser(LoginInfo loginInfo) {
        User user = null;
        if (!loginInfo.isValid()) {
            throw new LoginInfoNotEnoughException();
        }
        try {
            if (loginInfo.isUsernameValid()) {
                user =  getUserByUsername(loginInfo.getUsername());
            }
            if (loginInfo.isEmailValid()) {
                user =  getUserByEmail(loginInfo.getEmail());
            }
        } catch (UserNotFoundException e) {
            throw new UserNotAuthenticatedException();
        }

        if (!StringUtils.equals(loginInfo.getPassword(), user.getPassword())) {
            throw new UserNotAuthenticatedException();
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public User createUser(User user) {
        return userRepository.createUser(user);
    }

    public User updateUser(User user) {
        return userRepository.updateUser(user);
    }

    public void deleteUser(long userId) {
        userRepository.deleteUser(userId);
    }
}
