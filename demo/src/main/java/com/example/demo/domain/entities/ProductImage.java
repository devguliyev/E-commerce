package com.example.demo.domain.entities;

import com.example.demo.domain.entities.common.BaseAccountableEntity;
import com.example.demo.domain.enums.ImageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="product_images")
@Entity
public class ProductImage extends BaseAccountableEntity {



    @Enumerated(EnumType.STRING)
    @Column(name = "image_type")
    private ImageType imageType;

    //relational

    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "file_entity_id",unique = true)
    private FileEntity fileEntity;


}
