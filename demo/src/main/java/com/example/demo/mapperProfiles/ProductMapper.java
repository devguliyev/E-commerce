package com.example.demo.mapperProfiles;


import com.example.demo.domain.entities.Product;
import com.example.demo.domain.entities.ProductImage;
import com.example.demo.domain.enums.Currency;
import com.example.demo.domain.enums.ImageType;
import com.example.demo.domain.valueObjects.Money;
import com.example.demo.dto.productImages.GetImageInProductDto;
import com.example.demo.dto.products.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.mapstruct.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring",uses = ProductImageMapper.class)
public interface ProductMapper {

    @Mapping(target = "priceAmount",source = "product",qualifiedByName = "toPriceAmount")
    @Mapping(target = "currency",source = "product",qualifiedByName = "toCurrencyString")

    GetProductDto toGetProductDto(Product product);

    @Mapping(target = "priceAmount",source = "product",qualifiedByName = "toPriceAmount")
    @Mapping(target = "currency",source = "product",qualifiedByName = "toCurrencyString")
    @Mapping(target="primaryImage",source = "productImages",qualifiedByName = "mainImage")
    GetProductItemDto toGetProductItemDto(Product product);

    GetProductInCategoryDto toGetProductInCategoryDto(Product product);

    @Mapping(target = "productImages",ignore = true)
    @Mapping(target = "category",ignore = true)
    @Mapping(target="money",source = "productDto",qualifiedByName = "toMoney")
    Product toEntity(PostProductDto productDto);

    @Mapping(target = "productImages",ignore = true)
//    @Mapping(target = "category",ignore = true)
    @Mapping(target="money",source = "productDto",qualifiedByName = "toMoney")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntity(PutProductDto productDto, @MappingTarget Product entity);

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
    @Named("toMoney")
    default Money toMoney(PutProductDto productDto){
        return new Money(productDto.priceAmount(),productDto.currency());
    }



//
//    PutProductDto toPutProductDto(Product product);


}
