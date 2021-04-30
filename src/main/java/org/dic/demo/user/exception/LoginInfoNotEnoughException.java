package org.dic.demo.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Email or username is required")
public class LoginInfoNotEnoughException extends RuntimeException {

  private static final long serialVersionUID = -17907067130378594L;
}
