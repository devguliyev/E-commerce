package com.example.demo.dto.files;

import com.example.demo.domain.enums.UploadContext;
import lombok.Builder;

@Builder
public record FileInfoDto(String fileName, UploadContext context){
}
