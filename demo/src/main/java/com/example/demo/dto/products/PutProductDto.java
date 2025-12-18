package com.example.demo.dto.products;

import java.math.BigDecimal;

public record PutProductDto(
        String name,
        String sku,
        String description,
        BigDecimal priceAmount,
        String currency,
        Long categoryId
) {
}
