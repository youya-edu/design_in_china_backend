package org.dic.demo.user.database;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dic.demo.common.TransformableToDomain;
import org.dic.demo.user.model.User;
import org.dic.demo.user.model.UserKeyInfo;

@Getter
@Setter
@Builder
@ToString
public class DatabaseUser implements TransformableToDomain<User> {

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

  @Override
  public User toDomainObject() {
    return User.builder()
        .id(this.id)
        .userKeyInfo(
            UserKeyInfo.builder()
                .username(this.username)
                .email(this.email)
                .password(this.password)
                .build())
        .accountNonExpired(!this.accountExpired)
        .accountNonLocked(!this.accountLocked)
        .credentialsNonExpired(!this.credentialsExpired)
        .enabled(this.enabled)
        .nickname(this.getNickname())
        .avatar(this.avatar)
        .phone(this.phone)
        .description(this.description)
        .createdAt(this.createdAt)
        .build();
  }
}
