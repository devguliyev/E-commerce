package shop.API.core.application.dtos.products;

import shop.API.core.application.dtos.categories.GetCategoryInProductDto;

import java.math.BigDecimal;

public record GetProductInCategoryDto(
        Long id,
        String name
) {

    }
