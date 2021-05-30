package org.dic.demo.user.model;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum UserRole {
  ADMIN(1),
  DESIGNER(2),
  USER(3);

  private final int id;

  UserRole(int id) {
    this.id = id;
  }

  public static UserRole of(String val) {
    return Arrays.stream(UserRole.values())
        .filter(userRole -> userRole.name().equalsIgnoreCase(val))
        .findFirst()
        .orElse(null);
  }
}
