package org.dic.demo.user.resource;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ViewUserCollection {

  private List<ViewUser> users;
  private long totalSize;
}
