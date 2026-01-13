package com.example.demo.controller;

import com.example.demo.dto.products.GetProductDto;
import com.example.demo.dto.products.GetProductItemDto;
import com.example.demo.dto.products.PostProductDto;
import com.example.demo.dto.products.PutProductDto;
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
    public ResponseEntity<Page<GetProductItemDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int pageSize
    ){

        return ResponseEntity.ok(productService.getAll(page,pageSize));
    }

       @GetMapping("/{id}")
        public ResponseEntity<GetProductDto> getById(
                @PathVariable Long id
        ){
          return ResponseEntity.ok(productService.getById(id));
        }

        @PostMapping
        public ResponseEntity<Void> create(
                @Valid @RequestBody PostProductDto productDto
        ) {
            productService.create(productDto);

            return ResponseEntity.noContent().build();
        }

        @PutMapping("/{id}")
        public ResponseEntity<Void> update(
                @PathVariable Long id,
                @Valid @RequestBody PutProductDto productDto
                ){
            productService.update(id,productDto);
            return ResponseEntity.noContent().build();
        }

//        @GetMapping("/{id}/images")
//        public ResponseEntity<Void> getImages(
//                @RequestParam Long id
//        ){
//
//        }
//        @PostMapping("/{id}/images")
//        public ResponseEntity<Void> addImages(
//                @RequestParam Long id
//        ){
//
//        }

//        @PutMapping("/{id}/images/set-main")
//        public ResponseEntity<Void> updateMainImage(
//                @RequestParam Long id
//        ){
//
//        }
//
//        @DeleteMapping("/{id}/images")
//        public ResponseEntity<Void> deleteImages(
//                @RequestParam Long id
//        ){
//
//        }








}
