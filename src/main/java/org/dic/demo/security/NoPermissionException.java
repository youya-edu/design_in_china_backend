package org.dic.demo.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "No permission to do this operation")
public class NoPermissionException extends RuntimeException {

  private static final long serialVersionUID = 3575024385348211874L;
}
