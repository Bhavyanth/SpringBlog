package com.phoenix.blog.exceptions;

public class SpringPostException extends RuntimeException {
    public SpringPostException(String exMessage, Exception exception) {

        super(exMessage, exception);
    }

    public SpringPostException(String exMessage) {
        super(exMessage);
    }
}
