package org.dic.demo.user.database;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.dic.demo.user.model.User;
import org.dic.demo.user.model.UserKeyInfo;

@Getter
@Setter
@Builder
@ToString
public class DatabaseUser {

  private long id;
  private String username;
  private String email;
  private String password;
  private boolean accountExpired;
  private boolean accountLocked;
  private boolean credentialsExpired;
  private boolean enabled;
  private String nickname;
  private String avatar;
  private String phone;
  private String description;
  private Date createdAt;

  public static User asDomainObject(DatabaseUser databaseUser) {
    return User.builder()
        .id(databaseUser.id)
        .userKeyInfo(
            UserKeyInfo.builder()
                .username(databaseUser.username)
                .email(databaseUser.email)
                .password(databaseUser.password)
                .build()
        )
        .accountNonExpired(!databaseUser.accountExpired)
        .accountNonLocked(!databaseUser.accountLocked)
        .credentialsNonExpired(!databaseUser.credentialsExpired)
        .enabled(databaseUser.enabled)
        .nickname(databaseUser.getNickname())
        .avatar(databaseUser.avatar)
        .phone(databaseUser.phone)
        .description(databaseUser.description)
        .createdAt(databaseUser.createdAt)
        .build();
  }

  public static DatabaseUser fromDomainObject(User user) {
    return DatabaseUser.builder()
        .id(user.getId())
        .username(user.getUsername())
        .email(user.getEmail())
        .password(user.getPassword())
        .accountExpired(!user.isAccountNonExpired())
        .accountLocked(!user.isAccountNonLocked())
        .credentialsExpired(!user.isCredentialsNonExpired())
        .enabled(user.isEnabled())
        .nickname(user.getNickname())
        .avatar(user.getAvatar())
        .phone(user.getPhone())
        .description(user.getDescription())
        .createdAt(user.getCreatedAt())
        .build();
  }
}
