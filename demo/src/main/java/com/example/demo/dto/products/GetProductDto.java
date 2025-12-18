package com.example.demo.dto.products;


import com.example.demo.dto.categories.GetCategoryInProductDto;
import com.example.demo.dto.productImages.GetImageInProductDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record GetProductDto(
        Long id,
        String name,
        String sku,
        String description,
        BigDecimal priceAmount,
        String currency,
        GetCategoryInProductDto category,
        List<GetImageInProductDto> images,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {


}
