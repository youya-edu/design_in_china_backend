package org.dic.demo.user.resource.entity;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dic.demo.user.model.User;

@Setter
@Getter
@NoArgsConstructor
public class ApiUserCollection {

  private List<ApiUser> users;
  private int size;
}