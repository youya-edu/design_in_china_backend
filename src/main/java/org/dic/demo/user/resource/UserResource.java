package org.dic.demo.user.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.dic.demo.user.exception.UserNotFoundException;
import org.dic.demo.user.exception.UserUniqueViolationException;
import org.dic.demo.user.model.User;
import org.dic.demo.user.model.UserKeyInfo;
import org.dic.demo.user.service.UserChecker;
import org.dic.demo.user.service.UserService;
import org.dic.demo.util.HttpUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class UserResource {

  private final UserService userService;
  private final UserChecker userChecker;

  @GetMapping("/u/{username}")
  public ResponseEntity<ViewUser> getUser(@PathVariable("username") String username) {
    User user = userService.getUserByUsername(username);
    if (user == null) {
      throw new UserNotFoundException();
    }
    return ResponseEntity.ok(ViewUser.fromDomainObject(user));
  }

  @GetMapping("/users")
  public ResponseEntity<ViewUserCollection> getAllUsers() {
    List<User> users = userService.getAllUsers();
    if (users == null) {
      users = new ArrayList<>();
    }
    List<ViewUser> viewUsers =
        users.stream().map(ViewUser::fromDomainObject).collect(Collectors.toList());
    ViewUserCollection viewUserCollection =
        ViewUserCollection.builder().users(viewUsers).size(viewUsers.size()).build();
    return ResponseEntity.ok(viewUserCollection);
  }

  @PostMapping("/users")
  public ResponseEntity<User> signup(@RequestBody UserKeyInfo payload, HttpServletRequest req) {
    if (userChecker.isEmailExisted(payload) || userChecker.isUsernameExisted(payload)) {
      throw new UserUniqueViolationException();
    }
    User newUser = userService.createUser(payload);
    return ResponseEntity.created(HttpUtils.uriWithPath(req, String.valueOf(newUser.getId())))
        .body(newUser);
  }

  @PutMapping("/users")
  public ResponseEntity<ViewUser> updateUser(@RequestBody ViewUser payload) {
    User user = ViewUser.toDomainObject(payload);
    userService.updateUser(user);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/users/{userId}")
  public ResponseEntity<Void> deleteUser(@PathVariable("userId") long userId) {
    userService.deleteUser(userId);
    return ResponseEntity.noContent().build();
  }
}
