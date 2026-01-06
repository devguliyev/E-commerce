package com.example.demo.dto.products;

import com.example.demo.dto.productImages.GetImageInProductDto;

import java.math.BigDecimal;

public record GetProductItemDto(Long id,
                                String name,
                                BigDecimal priceAmount,
                                String currency,
                                String primaryImage
) {
}
