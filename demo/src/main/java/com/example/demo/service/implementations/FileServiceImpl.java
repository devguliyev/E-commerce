package com.example.demo.service.implementations;

import com.example.demo.domain.enums.FileSize;
import com.example.demo.domain.enums.FileType;
import com.example.demo.service.interfaces.FileService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
@Service
public class FileServiceImpl implements FileService {

    public boolean validateType(MultipartFile file, FileType type){
        if(file==null)
            throw new IllegalArgumentException("File is null");

        return switch (type){
            case FileType.IMAGE -> file.getContentType().contains("image/");
            case FileType.VIDEO -> file.getContentType().contains("video/");
            case FileType.AUDIO -> file.getContentType().contains("audio/");
            default -> false;
        };

    }
    public boolean validateSize(MultipartFile file, FileSize size,int value){
        if(file==null)
            throw new IllegalArgumentException("File is null");

        return switch (size) {
            case FileSize.KB -> file.getSize() < (long) value * 1024;
            case FileSize.MB -> file.getSize() < (long) value * 1024 * 1024;
            case FileSize.GB -> file.getSize() < (long) value * 1024 * 1024 * 1024;
            default -> false;
        };
    }
    public String createFile(MultipartFile file){

        String newFileName= UUID.randomUUID().toString()+   //random string
                StringUtils.getFilenameExtension(file.getOriginalFilename()); //subtracting extension of fileName

         return null;




    }
}
