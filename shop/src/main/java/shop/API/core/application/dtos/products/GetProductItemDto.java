package shop.API.core.application.dtos.products;

import shop.API.core.application.dtos.categories.GetCategoryInProductDto;

import java.math.BigDecimal;

public record GetProductItemDto(Long id,
                                String name,
                                BigDecimal priceAmount,
                                String currency,
                                String primaryImage
) {
}
