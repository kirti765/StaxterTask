package com.staxter.exception;

public class IncorrectUserNamePasswordException extends RuntimeException {
    public IncorrectUserNamePasswordException(String message) {
        super(message);
    }
}
