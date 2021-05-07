package org.dic.demo.user.resource;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ViewUserCollection {

  private List<ViewUser> users;
  private int size;
}
