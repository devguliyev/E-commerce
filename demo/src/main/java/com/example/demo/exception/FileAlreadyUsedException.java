package com.example.demo.exception;

import com.example.demo.domain.enums.FileType;
import com.example.demo.exception.common.BusinessException;
import org.springframework.http.HttpStatus;

public class FileAlreadyUsedException extends BusinessException {
    private static final String DEFAULT_MESSAGE="FileEntity is already used";

    //for default message
    public FileAlreadyUsedException(){
        this(DEFAULT_MESSAGE);
    }
    //for messages created in service
    public FileAlreadyUsedException(String message) {
        super(
                "FILE_ALREADY_USED",
                HttpStatus.BAD_REQUEST,
                message);
    }
    //for more detailed message
    public FileAlreadyUsedException(Long id){
        this(String.format("FileEntity with Id:'%d' is already used",id));

    }
}
