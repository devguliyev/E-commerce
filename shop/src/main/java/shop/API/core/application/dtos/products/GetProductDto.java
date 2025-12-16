package shop.API.core.application.dtos.products;

import jakarta.persistence.*;
import shop.API.core.application.dtos.categories.GetCategoryInProductDto;
import shop.API.core.application.dtos.productImages.GetImageInProductDto;
import shop.API.core.domain.entities.Category;
import shop.API.core.domain.valueObjects.Money;

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
