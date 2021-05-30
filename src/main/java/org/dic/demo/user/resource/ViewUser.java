package org.dic.demo.user.resource;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.dic.demo.common.TransformableToDomain;
import org.dic.demo.composition.resource.ViewCompositionCollection;
import org.dic.demo.user.model.User;
import org.dic.demo.user.model.UserKeyInfo;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class ViewUser implements TransformableToDomain<User> {

  private long id;
  private String username;
  private String email;
  private String nickname;
  private String avatar;
  private String phone;
  private String description;
  private Date createdAt;
  private ViewCompositionCollection compositionCollection;
  private List<String> followed;
  private List<String> following;

  @Override
  public User toDomainObject() {
    return User.builder()
        .id(this.id)
        .userKeyInfo(UserKeyInfo.builder().email(this.email).username(this.username).build())
        .nickname(this.nickname)
        .avatar(this.avatar)
        .description(this.description)
        .build();
  }
}
