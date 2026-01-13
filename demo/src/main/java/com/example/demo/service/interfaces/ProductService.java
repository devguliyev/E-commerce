package com.example.demo.service.interfaces;

import com.example.demo.dto.products.GetProductDto;
import com.example.demo.dto.products.GetProductItemDto;
import com.example.demo.dto.products.PostProductDto;
import com.example.demo.dto.products.PutProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public interface ProductService {
    GetProductDto getById(Long id);
    Page<GetProductItemDto> getAll(int page, int pageSize);
    void create(PostProductDto productDto);
    void update(Long id, PutProductDto productDto);
}
