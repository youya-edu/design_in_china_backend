package org.dic.demo.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.dic.demo.util.web.WebUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** Only for playing and learning, never use in production...!! */
@Profile("dev")
@RestController
public class BasicAuthenticationController {

  @GetMapping("/basic-authenticate")
  public ResponseEntity<String> authenticate(HttpServletRequest req, HttpServletResponse res) {
    String authorizationHeader = WebUtils.getAuthorizationHeader(req);

    // 如果存在Authorization Header
    if (AuthenticationType.isBasic(authorizationHeader)) {
      // 解码Base64
      String encodedBase64UsernamePassword = authorizationHeader.split(" ")[1];
      String usernamePassword =
          new String(Base64Utils.decodeFromString(encodedBase64UsernamePassword));
      String username = usernamePassword.split(":")[0];
      String password = usernamePassword.split(":")[1];

      if ("bad".equals(username) && "bad".equals(password)) {
        return ResponseEntity.ok("Hello bad guy.");
      }
    }

    // 还未认证或认证错误，则返回WWW-Authenticate Header
    res.setHeader(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Input username and password.\"");
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }
}
