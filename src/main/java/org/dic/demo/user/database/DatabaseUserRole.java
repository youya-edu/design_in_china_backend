package org.dic.demo.user.database;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DatabaseUserRole {
  private final long userId;
  private final int roleId;
}
