package org.dic.demo.user.service;

import org.dic.demo.user.model.User;
import org.dic.demo.user.model.UserKeyInfo;
import org.dic.demo.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserChecker {

  private final UserRepository userRepository;

  public UserChecker(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public boolean isEmailExisted(UserKeyInfo userKeyInfo) {
    if (!userKeyInfo.isEmailValid()) {
      return false;
    }
    User existedUser = userRepository.getUserByEmail(userKeyInfo.getEmail());
    return existedUser != null;
  }

  public boolean isUsernameExisted(UserKeyInfo userKeyInfo) {
    if (!userKeyInfo.isUsernameValid()) {
      return false;
    }
    User existedUser = userRepository.getUserByUsername(userKeyInfo.getUsername());
    return existedUser != null;
  }
}
