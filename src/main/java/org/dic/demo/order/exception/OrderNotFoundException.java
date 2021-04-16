package org.dic.demo.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -2525870210211476304L;

    public OrderNotFoundException(String message) {
        super(message);
    }
}
