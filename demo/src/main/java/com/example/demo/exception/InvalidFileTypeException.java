package com.example.demo.exception;

import com.example.demo.domain.enums.FileSize;
import com.example.demo.domain.enums.FileType;
import com.example.demo.exception.common.BusinessException;
import org.springframework.http.HttpStatus;

public class InvalidFileTypeException extends BusinessException {

    private static final String DEFAULT_MESSAGE="File type is invalid";

    //for default message
    public InvalidFileTypeException(){
        this(DEFAULT_MESSAGE);
    }
    //for messages created in service
    public InvalidFileTypeException(String message) {
        super(
                "INVALID_FILE_TYPE",
                HttpStatus.BAD_REQUEST,
                message);
    }
    //for more detailed message
    public InvalidFileTypeException(String fileName, FileType type){
        this(String.format("File '%s' type is not allowed. Allowed type: %s",fileName,type.toString()));

    }
}
