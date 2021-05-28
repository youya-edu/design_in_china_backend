package org.dic.demo.user.service;

import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.dic.demo.composition.repository.CompositionRepository;
import org.dic.demo.security.SecurityGuard;
import org.dic.demo.user.exception.LoginInfoNotEnoughException;
import org.dic.demo.user.exception.UserNotAuthenticatedException;
import org.dic.demo.user.model.User;
import org.dic.demo.user.model.UserKeyInfo;
import org.dic.demo.user.repository.UserRepository;
import org.dic.demo.util.media.MediaType;
import org.dic.demo.util.media.MediaUtils;
import org.dic.demo.util.web.WebHelper;
import org.dic.demo.util.web.WebUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final CompositionRepository compositionRepository;
  private final WebHelper webHelper;

  public User getUserById(long userId) {
    return userRepository.getUserById(userId);
  }

  public User getUserByUsername(String username) {
    User user = userRepository.getUserByUsername(username);
    if (user == null) {
      return null;
    }
    fillUserWithCompositions(user);
    return user;
  }

  public User getUserByEmail(String email) {
    return userRepository.getUserByEmail(email);
  }

  public List<User> getAllUsers() {
    List<User> users = userRepository.getAllUsers();
    users.forEach(this::fillUserWithCompositions);
    return users;
  }

  private void fillUserWithCompositions(User user) {
    user.setCompositions(compositionRepository.getCompositionsByUserId(user.getId()));
    user.getCompositions().forEach(composition -> composition.setAuthor(user));
  }

  public User createUser(UserKeyInfo userKeyInfo) {
    return userRepository.createUser(userKeyInfo);
  }

  public void updateUser(User user) {
    userRepository.updateUser(user);
  }

  public void deleteUser(long userId) {
    userRepository.deleteUser(userId);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return getUserByUsername(username);
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

  public String uploadAvatar(String targetUsername, String oldAvatarUrl, MultipartFile newAvatar)
      throws UsernameNotFoundException {
    // Check user's permission.
    // User can only change their own avatar.
    SecurityGuard.checkUserPermission(targetUsername);

    try {
      String path = WebUtils.resolvePathFromUrl(oldAvatarUrl);

      // Delete old avatar.
      MediaUtils.deleteFile(path);

      // Save new avatar.
      return webHelper.getOrigin() + MediaUtils.uploadFile(MediaType.AVATAR, newAvatar);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
