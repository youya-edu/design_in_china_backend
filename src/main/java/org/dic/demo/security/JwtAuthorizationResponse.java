package org.dic.demo.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dic.demo.user.resource.entity.ApiUser;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthorizationResponse {

  private String jwtToken;
  private ApiUser user;
}
