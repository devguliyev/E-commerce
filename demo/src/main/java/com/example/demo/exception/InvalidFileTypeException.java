package com.example.demo.exception;

public class InvalidFileTypeException extends RuntimeException{

    public InvalidFileTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFileTypeException(String message) {
        super(message);
    }
}
