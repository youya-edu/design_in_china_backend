package org.dic.demo.user.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.dic.demo.user.exception.LoginInfoNotEnoughException;
import org.dic.demo.user.exception.UserNotAuthenticatedException;
import org.dic.demo.user.model.User;
import org.dic.demo.user.model.UserKeyInfo;
import org.dic.demo.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  public User getUserById(long userId) {
    return userRepository.getUserById(userId);
  }

  public User getUserByUsername(String username) {
    return userRepository.getUserByUsername(username);
  }

  public User getUserByEmail(String email) {
    return userRepository.getUserByEmail(email);
  }

  public User authenticateUser(UserKeyInfo userKeyInfo) {
    User user = null;
    if (!userKeyInfo.isValid()) {
      throw new LoginInfoNotEnoughException();
    }
    if (userKeyInfo.isUsernameValid()) {
      user = getUserByUsername(userKeyInfo.getUsername());
    }
    if (userKeyInfo.isEmailValid()) {
      user = getUserByEmail(userKeyInfo.getEmail());
    }
    if (user == null || !StringUtils.equals(userKeyInfo.getPassword(), user.getPassword())) {
      throw new UserNotAuthenticatedException();
    }
    return user;
  }

  public List<User> getAllUsers() {
    return userRepository.getAllUsers();
  }

  public User createUser(UserKeyInfo userKeyInfo) {
    return userRepository.createUser(userKeyInfo);
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
