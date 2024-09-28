package com.arinc.util.exceptions.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorType {


    API_EXCEPTION_USER_NOT_FOUND(HttpStatus.NOT_FOUND, Type.VALIDATION_TYPE, "Пользователь {0} не найден");

    private final HttpStatus httpCode;
    private final Type errorType;
    private final String message;

    private enum Type {
        VALIDATION_TYPE
    }
}
