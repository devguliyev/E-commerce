package com.example.demo.requests;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record DeleteProductImagesRequest(
        @NotEmpty List<Long> imageIds

) {
}
