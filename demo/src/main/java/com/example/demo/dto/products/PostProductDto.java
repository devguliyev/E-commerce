package com.example.demo.dto.products;

import com.example.demo.domain.enums.Currency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
import java.util.List;

public record PostProductDto(
        @NotBlank String name,
        @NotBlank String sku,
        @NotBlank String description,
        @Positive BigDecimal priceAmount,
        @NotNull Currency currency,
        @NotNull Long categoryId,
        @NotNull MultipartFile primaryImage,
        List<MultipartFile> images
        ) {
}
