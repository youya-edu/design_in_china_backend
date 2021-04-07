package org.dic.demo.repository;

import org.dic.demo.model.User;
import org.dic.demo.repository.servicestub.UserServiceStub;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private final UserServiceStub userServiceStub;

    public UserRepository(UserServiceStub userServiceStub) {
        this.userServiceStub = userServiceStub;
    }

    public User getUserById(long userId) {
        return userServiceStub.getUserById(userId);
    }

    public List<User> getAllUsers() {
        return userServiceStub.getAllUsers();
    }

    public User createUser(User user) {
        return userServiceStub.createUser(user);
    }

    public User updateUser(User user) {
        return userServiceStub.updateUser(user);
    }

    public void deleteUser(long userId) {
        userServiceStub.deleteUser(userId);
    }
}
