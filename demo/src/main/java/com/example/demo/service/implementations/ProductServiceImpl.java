package com.example.demo.service.implementations;

import com.example.demo.domain.entities.Category;
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
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.interfaces.FileService;
import com.example.demo.service.interfaces.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final FileService fileService;
    private final ProductMapper productMapper;


    @Async
    public CompletableFuture<GetProductDto> getById(Long id){
        if(id==null)
            throw new IllegalArgumentException("Id is null");

        Product product=productRepository
                .findById(id)
                .orElseThrow(()->new NotFoundException(Product.class.getSimpleName(),id));

        return CompletableFuture.completedFuture(productMapper.toGetProductDto(product)) ;

    }

    @Async
    public CompletableFuture<Page<GetProductItemDto>> getAll(Pageable pageable){
        
        return CompletableFuture.completedFuture(productRepository.findAll(pageable).map(productMapper::toGetProductItemDto));
    }
    @Async
    public CompletableFuture<Void> create(PostProductDto productDto){

        if(productRepository.existsByName(productDto.name()))
            throw new EntityAlreadyExistsException("Product with name:"+productDto.name()+" already exists in Database");


        List<ProductImage> productImages=new ArrayList<>();
        productImages
                .add(createProductImage(productDto.primaryImage(),ImageType.PRIMARY));

        productDto
                .images()
                .forEach(img->productImages
                        .add(createProductImage(img,ImageType.SECONDARY)));

        Product product=productMapper.toEntity(productDto);
        product.setProductImages(productImages);
        Category category=categoryRepository
                .findById(productDto.categoryId())
                        .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        product.setCategory(category);

        product.setCreatedAt(LocalDateTime.now());

        productRepository.save(product);

        return CompletableFuture.completedFuture(null);

    }
    private ProductImage createProductImage(MultipartFile file, ImageType type){
        if(!fileService.validateSize(file, FileSize.MB,1))
            throw new InvalidFileSizeException("File size is invalid");

        if(!fileService.validateType(file,FileType.IMAGE))
            throw new InvalidFileTypeException("File type is invalid");

        ProductImage productImage=new ProductImage();
        productImage.setImage(fileService.createFile(file));
        productImage.setImageType(type);

        return productImage;

    }


}
