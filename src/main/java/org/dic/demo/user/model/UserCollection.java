package org.dic.demo.user.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCollection {
  private final List<User> users;
  private final long totalSize;
}
