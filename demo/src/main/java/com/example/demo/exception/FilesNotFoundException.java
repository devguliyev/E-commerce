package com.example.demo.exception;

import com.example.demo.exception.common.BusinessException;
import org.springframework.http.HttpStatus;

import java.util.List;

public class FilesNotFoundException extends BusinessException {
    private static final String DEFAULT_MESSAGE="Files were not found";

    //for default message
    public FilesNotFoundException(){
        this(DEFAULT_MESSAGE);
    }
    //for messages created in service
    public FilesNotFoundException(String message) {
        super(
                "FILES_NOT_FOUND",
                HttpStatus.NOT_FOUND,
                message);
    }
    //for more detailed message
    public FilesNotFoundException(List<Long> ids){
        this(String.format("FileEntities with Ids:'%s' were not found",ids));
    }
}
