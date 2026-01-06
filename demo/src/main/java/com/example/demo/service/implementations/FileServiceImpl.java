package com.example.demo.service.implementations;

import com.example.demo.domain.enums.FileSize;
import com.example.demo.domain.enums.FileType;
import com.example.demo.service.interfaces.FileService;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {


//    private final Path path;
//    public FileServiceImpl (@Value("${file.upload-dir}") String root){
//        path=Paths.get(root).toAbsolutePath().normalize();
//    }

    @Value("${minio.bucket}")
    private String bucket;
    private final MinioClient minioClient;
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

        String newFileName= UUID.randomUUID().toString()+ "." +   //random string
                StringUtils.getFilenameExtension(file.getOriginalFilename()); //subtracting extension of fileName

        try{
            minioClient.putObject(
                    PutObjectArgs
                            .builder()
                            .bucket(bucket)
                            .object(newFileName)
                            .stream(file.getInputStream(),
                                    file.getSize(),
                                    -1)
                            .contentType(file.getContentType())
                            .build()
            );
        }catch(Exception e){
            throw new RuntimeException(e);
        }

         return newFileName;
    }

//    public String createFile(MultipartFile file){
//
//        String newFileName= UUID.randomUUID().toString()+ "." +   //random string
//                StringUtils.getFilenameExtension(file.getOriginalFilename()); //subtracting extension of fileName
//
//        Path filePath=path.resolve(newFileName);
//        try {
//            file.transferTo(filePath.toFile());
//        }catch (IOException e){
//            throw new UncheckedIOException("Failed to save file",e);
//        }
////        Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);
//        return newFileName;
//    }
}
