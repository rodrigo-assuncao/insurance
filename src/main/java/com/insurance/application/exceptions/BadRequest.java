package com.insurance.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequest extends BusinessException {
    public BadRequest(String message) {
        super(message);
    }
}
