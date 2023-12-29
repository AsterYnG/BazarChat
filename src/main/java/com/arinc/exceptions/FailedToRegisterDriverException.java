package com.arinc.exceptions;

public class FailedToRegisterDriverException extends RuntimeException {
    public FailedToRegisterDriverException(String message) {

    }

    public FailedToRegisterDriverException() {
        super();
    }

    public FailedToRegisterDriverException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedToRegisterDriverException(Throwable cause) {
        super(cause);
    }

    protected FailedToRegisterDriverException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
