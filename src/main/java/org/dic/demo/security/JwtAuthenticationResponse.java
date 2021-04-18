package org.dic.demo.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dic.demo.user.model.User;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationResponse {

  private String jwtToken;
  private User user;
}
