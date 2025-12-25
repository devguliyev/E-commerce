package com.example.demo.exception.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

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

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class,
            BindException.class})
    public ResponseEntity<ApiError> handleValidationException(
            Exception ex,
            HttpServletRequest request

    ){
        String errors=extractValidationErrors(ex).toString();


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(
                        "VALIDATION_FAILED",
                        errors,
                        HttpStatus.BAD_GATEWAY.value(),
                        Instant.now(),
                        request.getRequestURI()

                ));
    };

    //for handling unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpectedException(
            Exception ex,
            HttpServletRequest request
    ){
        return ResponseEntity
                .status(
                HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ApiError(
                                "INTERNAL_ERROR",
                                ex.getMessage(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                Instant.now(),
                                request.getRequestURI()
                        )
                );

//        "Unexpected internal exception",
    }

    private Map<String,String> extractValidationErrors(Exception ex){
        if(ex instanceof MethodArgumentNotValidException manv){
            return manv.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            FieldError::getDefaultMessage,
                            (existing, replacement)->existing
                    ));

        } else if (ex instanceof ConstraintViolationException cve) {
            return cve.getConstraintViolations()
                    .stream()
                    .collect(Collectors.toMap(
                            v->v.getPropertyPath().toString(),
                            ConstraintViolation::getMessage,
                            (existing, replacement)->existing
                    ));
        }else if(ex instanceof BindException bindEx){
            return bindEx.getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            FieldError::getDefaultMessage,
                            (existing,replacement)->existing
                    ));

        }

        return  Map.of();
    }
}
