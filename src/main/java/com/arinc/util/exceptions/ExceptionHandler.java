package com.arinc.util.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleException(ApiException ex) {
        var errorResponse = ErrorResponse.builder(ex, ex.getErrorType().getHttpCode(), ex.getMessage()).build();
        return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);
    }
}
