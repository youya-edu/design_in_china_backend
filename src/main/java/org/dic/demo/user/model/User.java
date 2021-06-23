package org.dic.demo.user.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dic.demo.common.TransformableToDatabase;
import org.dic.demo.common.TransformableToView;
import org.dic.demo.composition.model.Composition;
import org.dic.demo.composition.model.CompositionCollection;
import org.dic.demo.composition.resource.ViewCompositionCollection;
import org.dic.demo.order.model.Order;
import org.dic.demo.user.database.DatabaseUser;
import org.dic.demo.user.resource.ViewUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
public class User
    implements UserDetails, TransformableToView<ViewUser>, TransformableToDatabase<DatabaseUser> {

  private static final long serialVersionUID = 4358298328226766436L;

  private final long id;
  private final UserKeyInfo userKeyInfo;
  @Default private Set<GrantedAuthority> authorities = new HashSet<>();
  private final boolean accountNonExpired;
  private final boolean accountNonLocked;
  private final boolean credentialsNonExpired;
  private final boolean enabled;
  private String nickname;
  private String avatar;
  private String phone;
  private String description;
  private transient Date createdAt;
  @Default private CompositionCollection compositionCollection = new CompositionCollection();
  @Default private List<UserRole> roles = new ArrayList<>(Collections.singletonList(UserRole.USER));
  @Default private List<Order> orders = new ArrayList<>();
  @Default private List<User> followed = new ArrayList<>();
  @Default private List<User> following = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getUsername() {
    return userKeyInfo.getUsername();
  }

  public String getEmail() {
    return userKeyInfo.getEmail();
  }

  @Override
  public String getPassword() {
    return userKeyInfo.getPassword();
  }

  @Override
  public boolean isAccountNonExpired() {
    return this.accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.accountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return this.credentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
  }

  @Override
  public ViewUser toViewObject() {
    return ViewUser.builder()
        .id(this.getId())
        .username(this.getUsername())
        .email(this.getEmail())
        .nickname(this.getNickname())
        .avatar(this.getAvatar())
        .phone(this.getPhone())
        .description(this.getDescription())
        .createdAt(this.getCreatedAt())
        .compositionCollection(this.getViewCompositionCollection())
        .followed(this.getFollowed().stream().map(User::getUsername).collect(Collectors.toList()))
        .following(this.getFollowing().stream().map(User::getUsername).collect(Collectors.toList()))
        .build();
  }

  private ViewCompositionCollection getViewCompositionCollection() {
    return new ViewCompositionCollection(
        this.getCompositionCollection().getCompositions().stream()
            .map(Composition::toViewObject)
            .collect(Collectors.toList()),
        this.getCompositionCollection().getTotalSize());
  }

  @Override
  public DatabaseUser toDatabaseObject() {
    return DatabaseUser.builder()
        .id(this.getId())
        .username(this.getUsername())
        .email(this.getEmail())
        .password(this.getPassword())
        .accountExpired(!this.isAccountNonExpired())
        .accountLocked(!this.isAccountNonLocked())
        .credentialsExpired(!this.isCredentialsNonExpired())
        .enabled(this.isEnabled())
        .nickname(this.getNickname())
        .avatar(this.getAvatar())
        .phone(this.getPhone())
        .description(this.getDescription())
        .createdAt(this.getCreatedAt())
        .build();
  }
}
