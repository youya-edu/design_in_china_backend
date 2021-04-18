package org.dic.demo.user.repository;

import java.util.List;
import org.dic.demo.user.model.User;
import org.dic.demo.user.servicestub.UserServiceStub;
import org.springframework.stereotype.Repository;

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
