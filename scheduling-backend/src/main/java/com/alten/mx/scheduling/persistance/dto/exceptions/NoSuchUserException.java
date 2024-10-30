package com.alten.mx.scheduling.persistance.dto.exceptions;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
