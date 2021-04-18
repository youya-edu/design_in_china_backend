package org.dic.demo.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
    code = HttpStatus.UNAUTHORIZED,
    reason = "User not authenticated"
)
public class UserNotAuthenticatedException extends RuntimeException {

  private static final long serialVersionUID = 8910081284290302171L;
}
