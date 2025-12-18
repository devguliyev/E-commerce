package com.example.demo.service.interfaces;

import com.example.demo.dto.products.GetProductDto;
import com.example.demo.dto.products.GetProductItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    GetProductDto getById(Long id);
    Page<GetProductItemDto> getAll(Pageable pageable);
}
