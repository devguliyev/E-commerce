package com.example.demo.exception;

import com.example.demo.exception.common.BusinessException;
import org.springframework.http.HttpStatus;

public class NotFoundException extends BusinessException {
    private static final String DEFAULT_MESSAGE="Entity not found";
    public NotFoundException(){
        this(DEFAULT_MESSAGE);
    }
    public NotFoundException(String message){
        super(
                "ENTITY_NOT_FOUND",
                HttpStatus.NOT_FOUND,
                message
        );
    }
    public NotFoundException(String entityName, Long id){
        this(String.format("%s entity with id %d is not found",entityName,id));
    }

}
