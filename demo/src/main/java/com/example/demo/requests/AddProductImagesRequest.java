package com.example.demo.requests;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record AddProductImagesRequest(
        @NotEmpty List<Long> fileIds
) {
}
