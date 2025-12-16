package shop.API.core.application.mapperProfiles;

import org.mapstruct.Mapper;
import shop.API.core.application.dtos.categories.*;
import shop.API.core.domain.entities.Category;

@Mapper(componentModel = "spring",uses=ProductMapper.class)
public interface CategoryMapper {

    GetCategoryDto toGetCategoryDto(Category category);
    GetCategoryItemDto toGetCategoryItemDto(Category category);

//    PostCategoryDto toPostCategoryDto(Category category);
//    PutCategoryDto toPutCategoryDto(Category category);

    GetCategoryInProductDto toGetCategoryInProductDto(Category category);
}
