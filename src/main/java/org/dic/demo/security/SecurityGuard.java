package org.dic.demo.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityGuard {

  private SecurityGuard() {}

  public static void checkUserPermission(String targetUsername) {
    String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
    if (!StringUtils.equals(targetUsername, currentUsername)) {
      throw new NoPermissionException();
    }
  }
}
