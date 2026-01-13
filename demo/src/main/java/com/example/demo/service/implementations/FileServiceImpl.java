package com.example.demo.service.implementations;

import com.example.demo.config.UploadPolicy;
import com.example.demo.domain.entities.FileEntity;
import com.example.demo.domain.enums.FileSize;
import com.example.demo.domain.enums.FileStatus;
import com.example.demo.domain.enums.FileType;
import com.example.demo.domain.enums.UploadContext;
import com.example.demo.dto.files.FileInfoDto;
import com.example.demo.exception.InvalidFileSizeException;
import com.example.demo.exception.InvalidFileTypeException;
import com.example.demo.mapperProfiles.FileEntityMapper;
import com.example.demo.repository.FileRepository;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private final UploadPolicy uploadPolicy;
    private final FileRepository fileRepository;
    private final FileEntityMapper fileEntityMapper;

    private boolean validateType(MultipartFile file, FileType type, List<String> minorTypes){
        if(file==null)
            throw new IllegalArgumentException("File is null");


        String major=switch (type){
            case FileType.IMAGE -> "image/";
            case FileType.VIDEO -> "video/";
            case FileType.AUDIO -> "audio/";
            default -> throw new InvalidFileTypeException("File type is invalid");
        };

        if(minorTypes !=null)
            return file.getContentType().contains(major);

        return minorTypes.stream().anyMatch(minor->file.getContentType().contains(major+minor));

//        return switch (type){
//            case FileType.IMAGE ->  file.getContentType().contains("image/");
//            case FileType.VIDEO -> file.getContentType().contains("video/");
//            case FileType.AUDIO -> file.getContentType().contains("audio/");
//            default -> false;
//        };

    }
    private boolean validateSize(MultipartFile file, FileSize size,int value){
        if(file==null)
            throw new IllegalArgumentException("File is null");

        return switch (size) {
            case FileSize.KB -> file.getSize() < (long) value * 1024;
            case FileSize.MB -> file.getSize() < (long) value * 1024 * 1024;
            case FileSize.GB -> file.getSize() < (long) value * 1024 * 1024 * 1024;
            default -> false;
        };
    }

    private void validateRule(MultipartFile file, UploadContext context){
        var rule=uploadPolicy.getRule(context);

        if(!validateSize(file,rule.getFileSize(),rule.getSizeValue()))
            throw new InvalidFileSizeException("File size is invalid");

        if(!validateType(file,rule.getFileType(),rule.getMinorTypes()))
            throw new InvalidFileTypeException("File type is invalid");

    }

    private FileInfoDto createFile(MultipartFile file, UploadContext context){

        String newFileName=generateUniqueFileName(file.getOriginalFilename()); //generates unique filename

        String path=saveToStorage(file,newFileName);
        FileEntity fileEntity=new FileEntity(
                file.getOriginalFilename(),
                newFileName,
                file.getContentType(),
                file.getSize(),
                path,
                FileStatus.TEMP
        );
        fileEntity.setCreatedAt(LocalDateTime.now());

        fileRepository.save(fileEntity);

        return fileEntityMapper.toFileInfoDto(fileEntity);
    }

    public FileInfoDto upload(MultipartFile file, UploadContext context){

        validateRule(file,context); //throw exception if validation fails

        return createFile(file,context);
    }

    public List<FileInfoDto> upload(List<MultipartFile> files, UploadContext context){
       files.forEach(file->validateRule(file,context));//throw exception if validation fails

       return files.stream()
                .map(f->createFile(f,context))
                .collect(Collectors.toList());
    }

    private String saveToStorage(MultipartFile file,String fileName){
        LocalDate date=LocalDate.now();
        String path="uploads/%d/%02d/%02d/%s"
                .formatted(
                        date.getYear(),
                        date.getMonthValue(),
                        date.getDayOfMonth(),
                        fileName
                );
        try{
            minioClient.putObject(
                    PutObjectArgs
                            .builder()
                            .bucket(bucket)
                            .object(path)
                            .stream(file.getInputStream(),
                                    file.getSize(),
                                    -1)
                            .contentType(file.getContentType())
                            .build()
            );
            return  path;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    private String generateUniqueFileName(String originalFileName ){
        return UUID.randomUUID().toString()+ "." +   //random string
                StringUtils.getFilenameExtension(originalFileName); //subtracting extension of fileName
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
