package com.example.demo.service.interfaces;

import com.example.demo.dto.products.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public interface ProductService {
    GetProductDto getById(Long id);
    Page<GetProductItemDto> getAll(ProductQueryDto queryDto);
    void create(PostProductDto productDto);
    void update(Long id, PutProductDto productDto);
    void updateMainImage(Long id, Long fileId);
    void addProductImages(Long id, List<Long> fileIds);
    void deleteProductImages(Long id, List<Long> imageIds);
}
