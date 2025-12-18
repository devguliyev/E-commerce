package com.example.demo.dto.products;

import java.math.BigDecimal;

public record GetProductItemDto(Long id,
                                String name,
                                BigDecimal priceAmount,
                                String currency,
                                String primaryImage
) {
}
