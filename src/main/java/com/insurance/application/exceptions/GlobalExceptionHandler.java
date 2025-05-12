package com.insurance.application.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handlerBusiness(BusinessException exception) {
        var responseStatus = exception.getClass().getAnnotation(ResponseStatus.class);
        return ResponseEntity.status(responseStatus.code()).body(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", responseStatus.code().value(),
                        "message", exception.getMessage()
                )
        );
    }

}
