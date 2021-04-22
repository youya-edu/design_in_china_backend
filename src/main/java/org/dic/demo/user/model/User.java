package org.dic.demo.user.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dic.demo.composition.model.Composition;
import org.dic.demo.order.model.Order;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
public class User implements UserDetails {

  private static final long serialVersionUID = 4358298328226766436L;

  private final long id;
  private final UserKeyInfo userKeyInfo;
  @Default
  private Set<GrantedAuthority> authorities = new HashSet<>();
  private final boolean accountNonExpired;
  private final boolean accountNonLocked;
  private final boolean credentialsNonExpired;
  private final boolean enabled;
  private String avatar;
  private String phone;
  private String description;
  private transient Date createdAt;
  @Default
  private List<Composition> compositions = new ArrayList<>();
  @Default
  private List<Order> orders = new ArrayList<>();
  @Default
  private List<User> followed = new ArrayList<>();
  @Default
  private List<User> following = new ArrayList<>();

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
}
