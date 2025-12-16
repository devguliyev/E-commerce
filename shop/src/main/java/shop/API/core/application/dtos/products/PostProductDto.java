package shop.API.core.application.dtos.products;

import shop.API.core.application.dtos.categories.GetCategoryInProductDto;

import java.math.BigDecimal;

public record PostProductDto(
                             String name,
                             String sku,
                             String description,
                             BigDecimal priceAmount,
                             String currency,
                             Long categoryId
) {
}
