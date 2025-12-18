package com.example.demo.service.implementations;

import com.example.demo.domain.entities.Product;
import com.example.demo.dto.products.GetProductDto;
import com.example.demo.mapperProfiles.ProductMapper;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

//    public ProductServiceImpl(
//            ProductRepository productRepository,
//            ProductMapper productMapper
//    ){
//        this.productRepository=productRepository;
//        this.productMapper=productMapper;
//    }

    @Override
    public GetProductDto getById(Long id){
        if(id==null) throw new IllegalArgumentException("Id is null");
        Product product=productRepository.findById(id).orElseThrow();

        return productMapper.toGetProductDto(product);

    }

//    public GetProductDto getById(Long id){
//
//        return null;
//
//    }


}
