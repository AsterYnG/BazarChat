package com.arinc.exceptions;

public class FailedToLoadPropertiesFileException extends RuntimeException {
    public FailedToLoadPropertiesFileException(String message) {
    }

    public FailedToLoadPropertiesFileException() {
        super();
    }

    public FailedToLoadPropertiesFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedToLoadPropertiesFileException(Throwable cause) {
        super(cause);
    }

    protected FailedToLoadPropertiesFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
