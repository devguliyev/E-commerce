package shop.API.core.application.mapperProfiles;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import shop.API.core.application.dtos.productImages.GetImageInProductDto;
import shop.API.core.domain.entities.ProductImage;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

    @Mapping(target = "imageType",source = "productImage",qualifiedByName = "toImageTypeString")
    GetImageInProductDto toGetImageInProductDto(ProductImage productImage);

    @Named("toImageTypeString")
    default String toImageTypeString(ProductImage productImage){
    return productImage.getImageType().toString();

    }

}
