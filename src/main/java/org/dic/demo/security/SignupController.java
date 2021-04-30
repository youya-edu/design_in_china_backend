package org.dic.demo.security;

import lombok.AllArgsConstructor;
import org.dic.demo.user.model.UserKeyInfo;
import org.dic.demo.user.service.UserChecker;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(
    value = "/signup",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class SignupController {

  private final UserChecker userChecker;

  @PostMapping("/check_email")
  public ResponseEntity<?> checkIfEmailExisted(@RequestBody UserKeyInfo userKeyInfo) {
    boolean emailExisted = userChecker.isEmailExisted(userKeyInfo);
    return emailExisted
        ? ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build()
        : ResponseEntity.ok().build();
  }

  @PostMapping("/check_username")
  public ResponseEntity<?> checkIfUsernameExisted(@RequestBody UserKeyInfo userKeyInfo) {
    boolean usernameExisted = userChecker.isUsernameExisted(userKeyInfo);
    return usernameExisted
        ? ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build()
        : ResponseEntity.ok().build();
  }
}
