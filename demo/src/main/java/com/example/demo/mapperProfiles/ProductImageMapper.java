package com.example.demo.mapperProfiles;


import com.example.demo.domain.entities.ProductImage;
import com.example.demo.domain.enums.ImageType;
import com.example.demo.dto.productImages.GetImageInProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

    @Mapping(target = "imageType",source = "productImage",qualifiedByName = "toImageTypeString")
    GetImageInProductDto toGetImageInProductDto(ProductImage productImage);

//    @Mapping(target = "mainImage",source = "productImage",qualifiedByName = "toImageTypeString")
//    GetImageInProductDto toGetMainImageInProductDto(List<ProductImage> productImages);

    List<GetImageInProductDto> toGetImageInProductDtoList(List<ProductImage> images);
    @Named("toImageTypeString")
    default String toImageTypeString(ProductImage productImage) {
        return productImage.getImageType().toString();
    }


        @Named("mainImage")
        default String toMainGetProductDto(List<ProductImage> productImages){
            return productImages
                    .stream()
                    .filter(pi->pi.getImageType()==ImageType.PRIMARY)
                    .findFirst()
                    .map(ProductImage::getImage)
                    .orElse(null)
                    ;
        }

    }


