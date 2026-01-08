package com.example.demo.controller;

import com.example.demo.dto.products.GetProductDto;
import com.example.demo.dto.products.GetProductItemDto;
import com.example.demo.dto.products.PostProductDto;
import com.example.demo.service.interfaces.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Validated
public class ProductController {

    private final ProductService productService;


    @GetMapping
    public ResponseEntity<Page<GetProductItemDto>> get(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int pageSize
    ){

        return ResponseEntity.ok(productService.getAll(page,pageSize));
    }

       @GetMapping("/{id}")
        public ResponseEntity<GetProductDto> get(
                @PathVariable Long id
        ){
          return ResponseEntity.ok(productService.getById(id));
        }

        @PostMapping
        public CompletableFuture<ResponseEntity<Void>> post(
                @Valid @RequestBody PostProductDto productDto
        ) {
            return productService.create(productDto)
                    .thenApply(v -> ResponseEntity.ok().build());
        }









}
