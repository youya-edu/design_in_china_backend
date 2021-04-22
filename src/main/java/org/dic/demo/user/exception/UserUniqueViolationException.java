package org.dic.demo.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
    code = HttpStatus.UNPROCESSABLE_ENTITY,
    reason = "User already existed"
)
public class UserUniqueViolationException extends RuntimeException {

  private static final long serialVersionUID = 2599999829650953714L;
}
