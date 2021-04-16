package org.dic.demo.composition.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CompositionNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -1156392346637456391L;

    public CompositionNotFoundException(String message) {
        super(message);
    }
}
