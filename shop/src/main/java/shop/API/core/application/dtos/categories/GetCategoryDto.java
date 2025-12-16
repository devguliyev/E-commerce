package shop.API.core.application.dtos.categories;

import shop.API.core.application.dtos.products.GetProductInCategoryDto;

import java.util.List;

public record GetCategoryDto(

        String name,

        List<GetProductInCategoryDto> products
) {

}
