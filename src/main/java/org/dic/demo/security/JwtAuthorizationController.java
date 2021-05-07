package org.dic.demo.security;

import org.dic.demo.user.model.User;
import org.dic.demo.user.model.UserKeyInfo;
import org.dic.demo.user.resource.ViewUser;
import org.dic.demo.user.service.UserService;
import org.dic.demo.util.JwtUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
    consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class JwtAuthorizationController {

  private final AuthenticationManager authenticationManager;
  private final UserService userService;

  public JwtAuthorizationController(
      AuthenticationManager authenticationManager, UserService userService) {
    this.authenticationManager = authenticationManager;
    this.userService = userService;
  }

  @PostMapping("/login")
  public ResponseEntity<JwtAuthorizationResponse> login(@RequestBody UserKeyInfo userKeyInfo) {
    User user = userService.authenticateUser(userKeyInfo);
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getUsername(), userKeyInfo.getPassword()));
    String jwtToken = JwtUtils.generateToken(user);
    return ResponseEntity.ok(new JwtAuthorizationResponse(jwtToken, ViewUser.from(user)));
  }
}
