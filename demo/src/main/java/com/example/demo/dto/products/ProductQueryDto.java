package com.example.demo.dto.products;

import com.example.demo.domain.enums.ProductSortField;
import com.example.demo.domain.enums.SortDirection;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductQueryDto(
        String search,
        ProductSortField sortBy,
        SortDirection sortDir,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Boolean isActive,
        Long categoryId,
        Integer page,
        Integer pageSize
) {
}
