package org.dic.demo.user.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.dic.demo.user.model.User;
import org.dic.demo.user.service.UserChecker;
import org.dic.demo.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/designers")
public class DesignerResource {

  private final UserService userService;
  private final UserChecker userChecker;

  @GetMapping
  public ResponseEntity<ViewUserCollection> getAllDesigners() {
    List<User> users = userService.getAllUsers();
    if (users == null) {
      users = new ArrayList<>();
    }
    List<ViewUser> viewUsers = users.stream().map(User::toViewObject).collect(Collectors.toList());
    ViewUserCollection viewUserCollection =
        ViewUserCollection.builder().users(viewUsers).size(viewUsers.size()).build();
    return ResponseEntity.ok(viewUserCollection);
  }
}
