package com.arinc.util.exceptions;

import com.arinc.database.entity.Message;
import com.arinc.util.exceptions.enums.ErrorType;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

@Getter
public class ApiException extends RuntimeException {
    private final ErrorType errorType;
    private final String message;

    public ApiException(final ErrorType errorType, Object... objects) {
        super((Throwable)null);
        this.message = buildMessage(errorType.getMessage(), Arrays.asList(objects));
        this.errorType = errorType;
    }

    private String buildMessage(String message, List<Object> list) {
        return MessageFormat.format(message, list);
    }
}
