package com.example.demo.repository;

import com.example.demo.domain.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity,Long> {
    List<FileEntity> getFileEntitiesByIds(List<Long> ids);
}
