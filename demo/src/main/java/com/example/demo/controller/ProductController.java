package com.example.demo.controller;

import com.example.demo.dto.products.GetProductDto;
import com.example.demo.dto.products.GetProductItemDto;
import com.example.demo.dto.products.PostProductDto;
import com.example.demo.service.interfaces.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
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
    public CompletableFuture<ResponseEntity<Page<GetProductItemDto>>> get(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int pageSize
    ){
        Pageable pageable= PageRequest.of(page,pageSize);
        return productService.getAll(pageable).thenApplyAsync(ResponseEntity::ok);
    }

       @GetMapping("/{id}")
        public CompletableFuture<ResponseEntity<GetProductDto>> get(
                @PathVariable Long id
        ){
          return productService.getById(id).thenApplyAsync(ResponseEntity::ok) ;
        }

        @PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public CompletableFuture<ResponseEntity<Void>> post(@ModelAttribute PostProductDto productDto
        ) {
            return productService.create(productDto)
                    .thenApply(v -> ResponseEntity.ok().build());
        }









}
