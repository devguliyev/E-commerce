package com.example.demo.mapperProfiles;


import com.example.demo.domain.entities.Product;
import com.example.demo.domain.enums.Currency;
import com.example.demo.domain.valueObjects.Money;
import com.example.demo.dto.products.GetProductDto;
import com.example.demo.dto.products.GetProductInCategoryDto;
import com.example.demo.dto.products.GetProductItemDto;
import com.example.demo.dto.products.PostProductDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "priceAmount",source = "product",qualifiedByName = "toPriceAmount")
    @Mapping(target = "currency",source = "product",qualifiedByName = "toCurrencyString")
    GetProductDto toGetProductDto(Product product);

    @Mapping(target = "priceAmount",source = "product",qualifiedByName = "toPriceAmount")
    @Mapping(target = "currency",source = "product",qualifiedByName = "toCurrencyString")
    GetProductItemDto toGetProductItemDto(Product product);

    GetProductInCategoryDto toGetProductInCategoryDto(Product product);

    @Mapping(target = "productImages",ignore = true)
    @Mapping(target = "category",ignore = true)
    @Mapping(target="money",source = "productDto",qualifiedByName = "toMoney")
    Product toEntity(PostProductDto productDto);

    @Named("toPriceAmount")
    default BigDecimal toPriceAmount(Product product){
        return product.getMoney().getAmount();
    }
    @Named("toCurrencyString")
    default String toCurrencyString(Product product){
        return product.getMoney().getCurrency().toString();
    }
    @Named("toMoney")
    default Money toMoney(PostProductDto productDto){
        return new Money(productDto.priceAmount(),productDto.currency());
    }



//
//    PutProductDto toPutProductDto(Product product);


}
