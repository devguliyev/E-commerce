package com.example.demo.mapperProfiles;


import com.example.demo.domain.entities.Product;
import com.example.demo.dto.products.GetProductDto;
import com.example.demo.dto.products.GetProductInCategoryDto;
import com.example.demo.dto.products.GetProductItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
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
