package com.blogspace.util.exception;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException() {}

    public UnauthorizedAccessException(String message) {
        super(message);
    }
}