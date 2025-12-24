package com.example.demo.exception;

import com.example.demo.exception.common.BusinessException;
import org.springframework.http.HttpStatus;

public class EntityAlreadyExistsException extends BusinessException {

    private static final String DEFAULT_MESSAGE="Enityt already exists";
    public EntityAlreadyExistsException(){
        this(DEFAULT_MESSAGE);
    }
    public EntityAlreadyExistsException(String message){
        super(
                "ENTITY_ALREADY_EXISTS",
                HttpStatus.CONFLICT,
                message
        );
    }
    public EntityAlreadyExistsException(String entityName,String value){
        this(String.format("%s entity with value %s already exists",entityName,value));
    }

//
//    public EntityAlreadyExistsException(String message, Throwable cause) {
//        super(message, cause);
//    }
//
//    public EntityAlreadyExistsException(String message) {
//        super(message);
//    }
}
