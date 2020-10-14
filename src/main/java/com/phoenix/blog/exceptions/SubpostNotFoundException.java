package com.phoenix.blog.exceptions;

public class SubpostNotFoundException extends RuntimeException {
    public SubpostNotFoundException(String message) {
        super(message);
    }
}
