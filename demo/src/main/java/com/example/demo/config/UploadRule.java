package com.example.demo.config;

import com.example.demo.domain.enums.FileSize;
import com.example.demo.domain.enums.FileType;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
@Getter
public class UploadRule {
    private final int sizeValue;
    private final FileSize fileSize;
    private final FileType fileType;
    private final List<String> minorTypes;

    public UploadRule(int sizeValue, FileSize fileSize, FileType fileType,String... minorTypes) {
        this.sizeValue = sizeValue;
        this.fileSize = fileSize;
        this.fileType = fileType;
        if(minorTypes==null)
            this.minorTypes = List.of("");//equivalent of *
        else
            this.minorTypes=List.of(minorTypes);
    }

}
