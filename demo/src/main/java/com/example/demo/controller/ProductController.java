package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

//    private final ProductService productService;


//       @GetMapping("/{id}")
//        public ResponseEntity<GetProductDto> getById(
//                @PathVariable Long id
//        ){
//          return ResponseEntity.ok(productService.getById(id));
//        }

    @GetMapping
    public ResponseEntity<String> get(

    ){
        return ResponseEntity.ok("test worked");
    }



}
