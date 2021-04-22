package org.dic.demo.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
public class UserKeyInfo {

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
