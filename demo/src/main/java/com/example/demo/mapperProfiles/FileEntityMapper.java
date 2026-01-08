package com.example.demo.mapperProfiles;

import com.example.demo.domain.entities.FileEntity;
import com.example.demo.dto.files.FileInfoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileEntityMapper {
    FileInfoDto toFileInfoDto(FileEntity entity);
}
