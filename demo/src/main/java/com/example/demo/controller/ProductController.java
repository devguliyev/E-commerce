package com.example.demo.controller;

import com.example.demo.dto.products.GetProductDto;
import com.example.demo.dto.products.GetProductItemDto;
import com.example.demo.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;


//        @GetMapping
//        public ResponseEntity<Page<GetProductItemDto>> get(Pageable pageable){
//
//            return ResponseEntity.ok(productService.getAll(pageable));
//        }

    @GetMapping
    public ResponseEntity<Page<GetProductItemDto>> get(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int pageSize
    ){
        Pageable pageable= PageRequest.of(page,pageSize);
        return ResponseEntity.ok(productService.getAll(pageable));
    }

       @GetMapping("/{id}")
        public ResponseEntity<GetProductDto> get(
                @PathVariable Long id
        ){
          return ResponseEntity.ok(productService.getById(id));
        }







}
