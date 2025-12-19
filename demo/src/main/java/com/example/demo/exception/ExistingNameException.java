package com.example.demo.exception;

public class ExistingNameException extends RuntimeException{

    public ExistingNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistingNameException(String message) {
        super(message);
    }
}
