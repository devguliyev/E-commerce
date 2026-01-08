package com.example.demo.service.implementations;

import com.example.demo.domain.entities.Category;
import com.example.demo.domain.entities.FileEntity;
import com.example.demo.domain.entities.Product;
import com.example.demo.domain.entities.ProductImage;
import com.example.demo.domain.enums.FileSize;
import com.example.demo.domain.enums.FileType;
import com.example.demo.domain.enums.ImageType;
import com.example.demo.dto.products.GetProductDto;
import com.example.demo.dto.products.GetProductItemDto;
import com.example.demo.dto.products.PostProductDto;
import com.example.demo.exception.EntityAlreadyExistsException;
import com.example.demo.exception.InvalidFileSizeException;
import com.example.demo.exception.InvalidFileTypeException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapperProfiles.ProductMapper;
import com.example.demo.mapperProfiles.ProductMapperImpl;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FileRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.interfaces.FileService;
import com.example.demo.service.interfaces.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileRepository fileRepository;
    private final ProductMapper productMapper;



    public GetProductDto getById(Long id){
        if(id==null)
            throw new IllegalArgumentException("Id is null");

        Product product=productRepository
                .findById(id)
                .orElseThrow(()->new NotFoundException(Product.class.getSimpleName(),id));

        return productMapper.toGetProductDto(product);

    }


    public Page<GetProductItemDto> getAll(int page, int pageSize){

        Pageable pageable=PageRequest.of(page,pageSize);
        return productRepository.findAll(pageable).map(productMapper::toGetProductItemDto);
    }
    @Async
    public CompletableFuture<Void> create(PostProductDto productDto){

        if(productRepository.existsByName(productDto.name()))
            throw new EntityAlreadyExistsException("Product with name:"+productDto.name()+" already exists in Database");

        Product product=productMapper.toEntity(productDto);
        product.setProductImages(new ArrayList<>());
        product.getProductImages()
                .add(createProductImage(productDto.primaryImageId(),ImageType.PRIMARY,product));
        productDto
                .imageIds()
                .forEach(img->product
                        .getProductImages()
                        .add(createProductImage(img,ImageType.SECONDARY,product)));

        Category category=categoryRepository
                .findById(productDto.categoryId())
                        .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        product.setCategory(category);

        product.setCreatedAt(LocalDateTime.now());

        productRepository.save(product);

        return CompletableFuture.completedFuture(null);

    }
    private ProductImage createProductImage(Long fileId,ImageType type, Product product){


        FileEntity fileEntity=fileRepository.findById(fileId).orElseThrow(()->
                new NotFoundException("Entity not found")
        );

        ProductImage productImage=new ProductImage();
        productImage.setFileEntity(fileEntity);
        productImage.setImageType(type);
        productImage.setProduct(product);
        productImage.setCreatedAt(LocalDateTime.now());

        return productImage;

    }


}
