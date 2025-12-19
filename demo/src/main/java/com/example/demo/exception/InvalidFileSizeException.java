package com.example.demo.exception;

public class InvalidFileSizeException extends RuntimeException{

    public InvalidFileSizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFileSizeException(String message) {
        super(message);
    }
}
