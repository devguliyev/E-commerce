package com.example.demo.mapperProfiles;


import com.example.demo.domain.entities.ProductImage;
import com.example.demo.dto.productImages.GetImageInProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

    @Mapping(target = "imageType",source = "productImage",qualifiedByName = "toImageTypeString")
    GetImageInProductDto toGetImageInProductDto(ProductImage productImage);

    @Named("toImageTypeString")
    default String toImageTypeString(ProductImage productImage){
    return productImage.getImageType().toString();

    }

}
