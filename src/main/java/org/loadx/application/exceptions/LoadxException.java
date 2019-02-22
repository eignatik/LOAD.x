package org.loadx.application.exceptions;

public class LoadxException extends RuntimeException {
    public LoadxException(String message, Throwable cause) {
        super(message, cause);
    }
}
