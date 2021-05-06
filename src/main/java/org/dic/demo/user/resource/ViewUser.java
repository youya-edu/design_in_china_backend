package org.dic.demo.user.resource;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.dic.demo.composition.resource.ViewComposition;
import org.dic.demo.user.model.User;
import org.dic.demo.user.model.UserKeyInfo;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class ViewUser {

  private long id;
  private String username;
  private String email;
  private String nickname;
  private String avatar;
  private String phone;
  private String description;
  private Date createdAt;
  private List<ViewComposition> compositions;
  private List<String> followed;
  private List<String> following;

  public static ViewUser from(User user) {
    return ViewUser.builder()
        .id(user.getId())
        .username(user.getUsername())
        .email(user.getEmail())
        .nickname(user.getNickname())
        .avatar(user.getAvatar())
        .phone(user.getPhone())
        .description(user.getDescription())
        .createdAt(user.getCreatedAt())
        .compositions(
            user.getCompositions().stream()
                .map(ViewComposition::fromDomainObject)
                .collect(Collectors.toList()))
        .followed(user.getFollowed().stream().map(User::getUsername).collect(Collectors.toList()))
        .following(user.getFollowing().stream().map(User::getUsername).collect(Collectors.toList()))
        .build();
  }

  public static User asDomainObject(ViewUser viewUser) {
    return User.builder()
        .id(viewUser.id)
        .userKeyInfo(
            UserKeyInfo.builder().email(viewUser.email).username(viewUser.username).build())
        .nickname(viewUser.nickname)
        .avatar(viewUser.avatar)
        .description(viewUser.description)
        .build();
  }
}
