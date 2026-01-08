package com.example.demo.domain.entities;

import com.example.demo.domain.entities.common.BaseAccountableEntity;
import com.example.demo.domain.enums.FileStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "file_entities")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileEntity extends BaseAccountableEntity {

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "stored_name")
    private String storedName;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "size")
    private Long size;

    @Column(name = "path")
    private String path;

    @Enumerated(EnumType.STRING)
    private FileStatus status;



}
