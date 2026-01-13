package com.example.demo.service.interfaces;

import com.example.demo.domain.entities.FileEntity;
import com.example.demo.domain.enums.UploadContext;
import com.example.demo.dto.files.FileInfoDto;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface FileService {

     FileInfoDto upload(MultipartFile file, UploadContext context);

     List<FileInfoDto> upload(List<MultipartFile> files, UploadContext context);

     FileEntity getFileEntity(Long id);

     void removeFile(Long id);



//     boolean validateType(MultipartFile file, FileType type);
//     boolean validateSize(MultipartFile file, FileSize size, int value);
//     String createFile(MultipartFile file);
}
