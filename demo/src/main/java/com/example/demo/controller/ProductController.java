package com.example.demo.controller;

import com.example.demo.dto.products.GetProductDto;
import com.example.demo.dto.products.GetProductItemDto;
import com.example.demo.dto.products.PostProductDto;
import com.example.demo.dto.products.PutProductDto;
import com.example.demo.requests.AddProductImagesRequest;
import com.example.demo.requests.DeleteProductImagesRequest;
import com.example.demo.service.interfaces.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/{id}/images/{fileId}/set-main")
        public ResponseEntity<Void> updateMainImage(
                @PathVariable Long id,
                @PathVariable Long fileId
        ){
            productService.updateMainImage(id,fileId);

            return ResponseEntity.noContent().build();
        }


//        @GetMapping("/{id}/images")
//        public ResponseEntity<Void> getImages(
//                @PathVariable Long id
//        ){
//
    //       }



    @PostMapping("/{id}/images")
    public ResponseEntity<Void> addImages(
            @PathVariable Long id,
            @Valid @RequestBody AddProductImagesRequest request){
        productService.addProductImages(id, request.fileIds());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/images")
    public ResponseEntity<Void> deleteImages(
            @PathVariable Long id,
            @Valid @RequestBody DeleteProductImagesRequest request){
        productService.deleteProductImages(id, request.imageIds());
        return ResponseEntity.noContent().build();
    }

}
