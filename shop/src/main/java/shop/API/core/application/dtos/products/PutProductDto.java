package shop.API.core.application.dtos.products;

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
