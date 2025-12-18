package com.example.demo.dto.categories;


import com.example.demo.dto.products.GetProductInCategoryDto;

import java.util.List;

public record GetCategoryDto(

        String name,

        List<GetProductInCategoryDto> products
) {

}
