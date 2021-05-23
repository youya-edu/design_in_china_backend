package org.dic.demo.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityGuard {

  private SecurityGuard() {}

  public static String getCurrentUsername() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  public static void checkUserPermission(String targetUsername) {
    String currentUsername = getCurrentUsername();
    if (!StringUtils.equals(targetUsername, currentUsername)) {
      throw new NoPermissionException();
    }
  }
}
