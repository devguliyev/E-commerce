package shop.API.core.application.mapperProfiles;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import shop.API.core.application.dtos.products.*;
import shop.API.core.domain.entities.Product;

import java.math.BigDecimal;

@Mapper(componentModel = "spring",uses = {CategoryMapper.class,ProductImageMapper.class})
public interface ProductMapper {

    @Mapping(target = "priceAmount",source = "product",qualifiedByName = "toPriceAmount")
    @Mapping(target = "currency",source = "product",qualifiedByName = "toCurrencyString")
    GetProductDto toGetProductDto(Product product);

    @Mapping(target = "priceAmount",source = "product",qualifiedByName = "toPriceAmount")
    @Mapping(target = "currency",source = "product",qualifiedByName = "toCurrencyString")
    GetProductItemDto toGetProductItemDto(Product product);

    GetProductInCategoryDto toGetProductInCategoryDto(Product product);


    @Named("toPriceAmount")
    default BigDecimal toPriceAmount(Product product){
        return product.getMoney().getAmount();
    }
    @Named("toCurrencyString")
    default String toCurrencyString(Product product){
        return product.getMoney().getCurrency().toString();
    }

//    PostProductDto toPostProductDto(Product product);
//
//    PutProductDto toPutProductDto(Product product);


}
