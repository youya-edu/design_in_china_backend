package org.dic.demo.user.model;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserCollection {

  private List<User> users;
  private int size;
}
