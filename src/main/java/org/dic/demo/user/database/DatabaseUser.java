package org.dic.demo.user.database;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.dic.demo.user.model.User;
import org.dic.demo.user.model.UserKeyInfo;

@Getter
@Setter
@NoArgsConstructor
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
        .avatar(databaseUser.avatar)
        .phone(databaseUser.phone)
        .description(databaseUser.description)
        .createdAt(databaseUser.createdAt)
        .build();
  }

  public static DatabaseUser fromDomainObject(User user) {
    DatabaseUser databaseUser = new DatabaseUser();
    databaseUser.setId(user.getId());
    databaseUser.setUsername(user.getUsername());
    databaseUser.setEmail(user.getEmail());
    databaseUser.setPassword(user.getPassword());
    databaseUser.setAccountExpired(!user.isAccountNonExpired());
    databaseUser.setAccountLocked(!user.isAccountNonLocked());
    databaseUser.setCredentialsExpired(!user.isCredentialsNonExpired());
    databaseUser.setEnabled(user.isEnabled());
    databaseUser.setAvatar(user.getAvatar());
    databaseUser.setPhone(user.getPhone());
    databaseUser.setDescription(user.getDescription());
    databaseUser.setCreatedAt(user.getCreatedAt());
    return databaseUser;
  }
}
