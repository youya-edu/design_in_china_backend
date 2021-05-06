package org.dic.demo.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dic.demo.user.resource.ViewUser;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthorizationResponse {

  private String jwtToken;
  private ViewUser user;
}
