package org.dic.demo.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoginInfo {

  private String username;
  private String email;
  private String password;

  public boolean isValid() {
    return isUsernameValid() || isEmailValid();
  }

  public boolean isUsernameValid() {
    return StringUtils.hasText(this.username);
  }

  public boolean isEmailValid() {
    return StringUtils.hasText(email);
  }
}
