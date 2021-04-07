package org.dic.demo.service;

import org.dic.demo.model.User;
import org.dic.demo.repository.UserRepository;
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
