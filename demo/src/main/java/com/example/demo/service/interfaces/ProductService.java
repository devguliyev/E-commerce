package com.example.demo.service.interfaces;

import com.example.demo.dto.products.GetProductDto;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    GetProductDto getById(Long id);
}
