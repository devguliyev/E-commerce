package com.example.demo.mapperProfiles;


import com.example.demo.domain.entities.Category;
import com.example.demo.dto.categories.GetCategoryDto;
import com.example.demo.dto.categories.GetCategoryInProductDto;
import com.example.demo.dto.categories.GetCategoryItemDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses=ProductMapper.class)
public interface CategoryMapper {

    GetCategoryDto toGetCategoryDto(Category category);
    GetCategoryItemDto toGetCategoryItemDto(Category category);

//    PostCategoryDto toPostCategoryDto(Category category);
//    PutCategoryDto toPutCategoryDto(Category category);

    GetCategoryInProductDto toGetCategoryInProductDto(Category category);
}
