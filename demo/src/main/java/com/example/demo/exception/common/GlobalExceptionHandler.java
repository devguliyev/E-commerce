package com.example.demo.exception.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler{

    //for handling business logic exceptions
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusinessException(
            BusinessException ex,
            HttpServletRequest request
    ){

        return ResponseEntity
                .status(ex.getStatus())
                .body(new ApiError(
                        ex.getCode(),
                        ex.getMessage(),
                        ex.getStatus().value(),
                        Instant.now(),
                        request.getRequestURI()
                ));
    }

    //for handling unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handledUnexpectedException(
            Exception ex,
            HttpServletRequest request
    ){
        return ResponseEntity
                .status(
                HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ApiError(
                                "INTERNAL_ERROR",
                                "Unexpected internal exception",
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                Instant.now(),
                                request.getRequestURI()
                        )
                );
    }
}
