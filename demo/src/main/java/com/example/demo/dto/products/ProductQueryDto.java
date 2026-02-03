package com.example.demo.dto.products;

import java.math.BigDecimal;

public record ProductQueryDto(
        String search,
        String sortBy,
        String sortDir,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Boolean isActive,
        Long categoryId,
        Integer page,
        Integer pageSize
) {
}
