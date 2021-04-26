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
import org.dic.demo.user.model.UserKeyInfo;
import org.springframework.security.core.GrantedAuthority;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class ApiUser {

  private long id;
  private String username;
  private String email;
  private Set<GrantedAuthority> authorities;
  private String nickname;
  private String avatar;
  private String phone;
  private String description;
  private Date createdAt;
  private List<Long> compositions;
  private List<String> followed;
  private List<String> following;

  public static ApiUser from(User user) {
    return ApiUser.builder()
        .id(user.getId())
        .username(user.getUsername())
        .email(user.getEmail())
        .authorities(new HashSet<>(user.getAuthorities()))
        .nickname(user.getNickname())
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

  public static User asDomainObject(ApiUser apiUser) {
    return User.builder()
        .id(apiUser.id)
        .userKeyInfo(UserKeyInfo.builder()
            .email(apiUser.email)
            .username(apiUser.username)
            .build())
        .nickname(apiUser.nickname)
        .avatar(apiUser.avatar)
        .description(apiUser.description)
        .build();
  }
}
