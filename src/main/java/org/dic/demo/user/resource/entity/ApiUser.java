package org.dic.demo.user.resource.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.dic.demo.composition.model.Composition;
import org.dic.demo.user.model.User;
import org.springframework.security.core.GrantedAuthority;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class ApiUser {

  private String username;
  private String email;
  private Set<GrantedAuthority> authorities;
  private String avatar;
  private String phone;
  private String description;
  private Date createdAt;
  private List<Long> compositions;
  private List<String> followed;
  private List<String> following;

  public static ApiUser from(User user) {
    return ApiUser.builder()
        .username(user.getUsername())
        .email(user.getEmail())
        .authorities(new HashSet<>(user.getAuthorities()))
        .avatar(user.getAvatar())
        .phone(user.getPhone())
        .description(user.getDescription())
        .createdAt(user.getCreatedAt())
        .compositions(
            user.getCompositions().stream().map(Composition::getId).collect(Collectors.toList()))
        .followed(user.getFollowed().stream().map(User::getUsername).collect(Collectors.toList()))
        .following(user.getFollowing().stream().map(User::getUsername).collect(Collectors.toList()))
        .build();
  }
}
