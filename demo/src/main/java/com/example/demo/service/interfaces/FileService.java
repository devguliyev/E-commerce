package com.example.demo.service.interfaces;

import com.example.demo.domain.enums.FileSize;
import com.example.demo.domain.enums.FileType;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

public interface FileService {
     boolean validateType(MultipartFile file, FileType type);
     boolean validateSize(MultipartFile file, FileSize size, int value);
     String createFile(MultipartFile file);
}
