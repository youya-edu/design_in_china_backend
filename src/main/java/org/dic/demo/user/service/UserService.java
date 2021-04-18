package org.dic.demo.user.service;

import org.apache.commons.lang3.StringUtils;
import org.dic.demo.user.exception.LoginInfoNotEnoughException;
import org.dic.demo.user.exception.UserNotAuthenticatedException;
import org.dic.demo.user.exception.UserNotFoundException;
import org.dic.demo.user.model.User;
import org.dic.demo.user.repository.UserRepository;
import org.dic.demo.security.LoginInfo;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

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
        if (loginInfo.isUsernameValid()) {
            user =  getUserByUsername(loginInfo.getUsername());
        }
        if (loginInfo.isEmailValid()) {
            user =  getUserByEmail(loginInfo.getEmail());
        }
        assert user != null;
        if (!StringUtils.equals(loginInfo.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Bad Credentials");
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByUsername(username);
    }
}
