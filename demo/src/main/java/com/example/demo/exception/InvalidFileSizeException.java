package com.example.demo.exception;

import com.example.demo.domain.enums.FileSize;
import com.example.demo.exception.common.BusinessException;
import org.springframework.http.HttpStatus;

public class InvalidFileSizeException extends BusinessException {


    private static final String DEFAULT_MESSAGE="File size is invalid";

    //for default message
    public InvalidFileSizeException(){
        this(DEFAULT_MESSAGE);
    }
    
    //for messages created in service
    public InvalidFileSizeException(String message) {
        super(
                "INVALID_FILE_SIZE",
                HttpStatus.BAD_REQUEST,
                message);
    }
    //for more detailed message
    public InvalidFileSizeException(String fileName, long value, FileSize size){
        this(String.format("File '%s' size must be less than %d %s",fileName,value,size.toString()));

    }
}
