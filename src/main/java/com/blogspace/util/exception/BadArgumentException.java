package com.blogspace.util.exception;

public class BadArgumentException extends RuntimeException {
    public BadArgumentException() {}

    public BadArgumentException(String message) {
        super(message);
    }
}
