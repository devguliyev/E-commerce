package com.example.demo.controller;

import com.example.demo.domain.enums.UploadContext;
import com.example.demo.dto.files.FileInfoDto;
import com.example.demo.service.interfaces.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/uploads")
@Validated
public class FileController {

    private final FileService fileService;

    @PostMapping
    public ResponseEntity<FileInfoDto> create(
            @RequestParam MultipartFile file,
            @RequestParam UploadContext context
            ){
        return ResponseEntity.ok(fileService.upload(file,context));
    }
    @PostMapping("/multi")
    public ResponseEntity<List<FileInfoDto>> createAll(
            @RequestParam List<MultipartFile> files,
            @RequestParam UploadContext context
    ){

        return ResponseEntity.ok(fileService.upload(files,context));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ){
        fileService.removeFile(id);
        return ResponseEntity.noContent().build();
    }

}
