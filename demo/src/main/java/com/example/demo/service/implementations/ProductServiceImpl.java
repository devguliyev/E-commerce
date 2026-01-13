package com.example.demo.service.implementations;

import com.example.demo.domain.entities.Category;
import com.example.demo.domain.entities.FileEntity;
import com.example.demo.domain.entities.Product;
import com.example.demo.domain.entities.ProductImage;
import com.example.demo.domain.enums.FileStatus;
import com.example.demo.domain.enums.ImageType;
import com.example.demo.dto.products.GetProductDto;
import com.example.demo.dto.products.GetProductItemDto;
import com.example.demo.dto.products.PostProductDto;
import com.example.demo.dto.products.PutProductDto;
import com.example.demo.exception.EntityAlreadyExistsException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapperProfiles.ProductMapper;
import com.example.demo.repository.FileRepository;
import com.example.demo.repository.ProductImageRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.interfaces.CategoryService;
import com.example.demo.service.interfaces.FileService;
import com.example.demo.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final CategoryService categoryService;
    private final FileService fileService;
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

    public void create(PostProductDto productDto){

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

        Category category=categoryService.getCategory(productDto.categoryId());

        product.setCategory(category);

        product.setCreatedAt(LocalDateTime.now());

        productRepository.save(product);

    }

    public void update(Long id,PutProductDto productDto){
        if(id==null)
            throw new IllegalArgumentException("Id is null");

       Product existed=productRepository
               .findById(id)
               .orElseThrow(()->new NotFoundException(Product.class.getSimpleName(),id));
       if(!productDto.name().equals(existed.getName())){
           if(productRepository.existsByName(productDto.name()))
                throw new EntityAlreadyExistsException("Product with name:"+productDto.name()+" already exists in Database");
       }
       if(!existed.getCategory().getId().equals(productDto.categoryId())){
           Category category=categoryService
                   .getCategory(productDto.categoryId());

           existed.setCategory(category);
        }

       productMapper.toEntity(productDto,existed);
       existed.setUpdatedAt(LocalDateTime.now());
       productRepository.save(existed);

//       productMapper.toEntity()
    }

    private @NonNull ProductImage createProductImage(Long fileId, ImageType type, Product product){

        FileEntity fileEntity=fileService.getFileEntity(fileId);

        ProductImage productImage=new ProductImage();
        productImage.setFileEntity(fileEntity);
        productImage.setImageType(type);
        productImage.setProduct(product);
        productImage.setCreatedAt(LocalDateTime.now());

        fileEntity.setStatus(FileStatus.USED);
        //fileRepository.save(fileEntity) no need Jpa Tracks the entity

        return productImage;

    }

    public void updateMainImage(Long id, Long fileId)
    {
        if(id==null)
            throw new IllegalArgumentException("Product Id is null");

        Product product=productRepository
                .findById(id)
                .orElseThrow(()->new NotFoundException(Product.class.getSimpleName(),id));

        ProductImage main=createProductImage(fileId,ImageType.PRIMARY,product);

        ProductImage existedMain=productImageRepository
                .findProductImageByProduct_IdAndImageType(product.getId(),ImageType.PRIMARY);
        if(existedMain!=null){
            fileService.removeFile(existedMain.getFileEntity().getId());
            productImageRepository.delete(existedMain);
        }
        productImageRepository.save(main);

    }
}
