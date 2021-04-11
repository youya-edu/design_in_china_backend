package org.dic.demo.repository;

import org.dic.demo.model.User;
import org.dic.demo.repository.servicestub.UserServiceStub;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    public User getUserById(long userId) {
        return UserServiceStub.getUserById(userId);
    }

    public User getUserByUsername(String username) {
        return UserServiceStub.getUserByUsername(username);
    }

    public User getUserByEmail(String email) {
        return UserServiceStub.getUserByEmail(email);
    }

    public List<User> getAllUsers() {
        return UserServiceStub.getAllUsers();
    }

    public User createUser(User user) {
        return UserServiceStub.createUser(user);
    }

    public User updateUser(User user) {
        return UserServiceStub.updateUser(user);
    }

    public void deleteUser(long userId) {
        UserServiceStub.deleteUser(userId);
    }
}
