package org.dic.demo.user.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.dic.demo.user.exception.UserNotFoundException;
import org.dic.demo.user.exception.UserUniqueViolationException;
import org.dic.demo.user.model.User;
import org.dic.demo.user.model.UserKeyInfo;
import org.dic.demo.user.resource.entity.ApiUser;
import org.dic.demo.user.resource.entity.ApiUserCollection;
import org.dic.demo.user.service.UserChecker;
import org.dic.demo.user.service.UserService;
import org.dic.demo.util.HttpUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/users")
public class UserResource {

  private final UserService userService;
  private final UserChecker userChecker;

  @GetMapping("/{username}")
  public ResponseEntity<ApiUser> getUser(@PathVariable("username") String username) {
    User user = userService.getUserByUsername(username);
    if (user == null) {
      throw new UserNotFoundException();
    }
    return ResponseEntity.ok(ApiUser.from(user));
  }

  @GetMapping
  public ResponseEntity<ApiUserCollection> getAllUsers() {
    List<User> users = userService.getAllUsers();
    if (users == null) {
      users = new ArrayList<>();
    }
    List<ApiUser> apiUsers = users.stream().map(ApiUser::from)
        .collect(Collectors.toList());
    ApiUserCollection apiUserCollection = new ApiUserCollection();
    apiUserCollection.setUsers(apiUsers);
    apiUserCollection.setSize(apiUsers.size());
    return ResponseEntity.ok(apiUserCollection);
  }

  @PostMapping
  public ResponseEntity<User> signup(@RequestBody UserKeyInfo payload, HttpServletRequest req) {
    if (userChecker.isEmailExisted(payload) || userChecker.isUsernameExisted(payload)) {
      throw new UserUniqueViolationException();
    }
    User newUser = userService.createUser(payload);
    return ResponseEntity
        .created(HttpUtils.uriWithPath(req, String.valueOf(newUser.getId())))
        .body(newUser);
  }

  @PutMapping
  public ResponseEntity<User> updateUser(@RequestBody User payload) {
    return ResponseEntity.ok(userService.updateUser(payload));
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> deleteUser(@PathVariable("userId") long userId) {
    userService.deleteUser(userId);
    return ResponseEntity.noContent().build();
  }
}
