package com.example.demo.repository;

import com.example.demo.domain.entities.ProductImage;
import com.example.demo.domain.enums.ImageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
    ProductImage findProductImageByProduct_IdAndImageType(Long productId, ImageType imageType);
//    List<ProductImage> findProductImagesByIds(List<Long> ids);
    List<ProductImage> findProductImagesByProduct_IdAndImageType(Long productId, ImageType imageType);
}
