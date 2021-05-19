package org.dic.demo.user.resource;

import java.net.URI;
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
import org.dic.demo.util.web.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserResource {

  private final UserService userService;
  private final UserChecker userChecker;

  @GetMapping("/{username}")
  public ResponseEntity<ViewUser> getUser(@PathVariable("username") String username) {
    User user = userService.getUserByUsername(username);
    if (user == null) {
      throw new UserNotFoundException();
    }
    return ResponseEntity.ok(user.toViewObject());
  }

  @GetMapping
  public ResponseEntity<ViewUserCollection> getAllUsers() {
    List<User> users = userService.getAllUsers();
    if (users == null) {
      users = new ArrayList<>();
    }
    List<ViewUser> viewUsers = users.stream().map(User::toViewObject).collect(Collectors.toList());
    ViewUserCollection viewUserCollection =
        ViewUserCollection.builder().users(viewUsers).size(viewUsers.size()).build();
    return ResponseEntity.ok(viewUserCollection);
  }

  @PostMapping
  public ResponseEntity<User> signup(@RequestBody UserKeyInfo payload, HttpServletRequest req) {
    if (userChecker.isEmailExisted(payload) || userChecker.isUsernameExisted(payload)) {
      throw new UserUniqueViolationException();
    }
    User newUser = userService.createUser(payload);
    return ResponseEntity.created(WebUtils.uriWithPath(req, String.valueOf(newUser.getId())))
        .body(newUser);
  }

  @PutMapping
  public ResponseEntity<ViewUser> updateUser(@RequestBody ViewUser viewUser) {
    User user = viewUser.toDomainObject();
    userService.updateUser(user);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> deleteUser(@PathVariable("userId") long userId) {
    userService.deleteUser(userId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping(value = "/{username}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ViewAvatar> uploadAvatar(
      @PathVariable("username") String username,
      @RequestParam("oldUrl") String oldAvatar,
      @RequestParam("avatar") MultipartFile newAvatar) {
    String newAvatarUrl = userService.uploadAvatar(username, oldAvatar, newAvatar);
    return ResponseEntity.created(URI.create(newAvatarUrl)).body(new ViewAvatar(newAvatarUrl));
  }
}
