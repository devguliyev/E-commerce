package com.example.demo.domain.enums;

import lombok.Getter;

@Getter
public enum ProductSortField {
    NAME("name"),
    DESCRIPTION("description"),
    SKU("sku"),
    AMOUNT("amount"),
    CURRENCY("currency"),
    CREATED_AT("createdAt");

    private final String sortField;

    ProductSortField(String sortField){
        this.sortField=sortField;
    }

}
