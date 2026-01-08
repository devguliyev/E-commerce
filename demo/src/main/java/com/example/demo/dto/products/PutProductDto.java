package com.example.demo.dto.products;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record PutProductDto(
        String name,
        String sku,
        String description,
        BigDecimal priceAmount,
        String currency,
        Long categoryId,

        Long primaryImageId,
        List<Long> imageIds
) {
}
