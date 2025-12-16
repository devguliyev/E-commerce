package shop.API.infrastructure.persistence.DAL.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import shop.API.core.application.dtos.products.GetProductDto;
import shop.API.core.application.interfaces.repositories.ProductRepository;
import shop.API.core.application.interfaces.services.ProductService;
import shop.API.core.application.mapperProfiles.ProductMapper;
import shop.API.core.domain.entities.Product;
@Component
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
