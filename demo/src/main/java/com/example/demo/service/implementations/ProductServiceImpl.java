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
import com.example.demo.exception.FileAlreadyUsedException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapperProfiles.ProductMapper;
import com.example.demo.repository.ProductImageRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.interfaces.CategoryService;
import com.example.demo.service.interfaces.FileService;
import com.example.demo.service.interfaces.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
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

    }

    private @NonNull ProductImage createProductImage(Long fileId, ImageType type, Product product){

        FileEntity fileEntity=fileService.getFileEntity(fileId);
        fileService.validateFileInUseStatus(fileEntity);

        ProductImage productImage=new ProductImage();
        productImage.setFileEntity(fileEntity);
        productImage.setImageType(type);
        productImage.setProduct(product);
        productImage.setCreatedAt(LocalDateTime.now());

        fileService.changeStatus(fileEntity,FileStatus.USED);

        return productImage;

    }

    @Transactional
    public void updateMainImage(Long id, Long fileId) {
        if(id==null)
            throw new IllegalArgumentException("Product Id is null");

        Product product=productRepository
                .findById(id)
                .orElseThrow(()->new NotFoundException(Product.class.getSimpleName(),id));

        ProductImage main=createProductImage(fileId,ImageType.PRIMARY,product);

        ProductImage existedMain=productImageRepository
                .findProductImageByProduct_IdAndImageType(product.getId(),ImageType.PRIMARY);

        if(existedMain!=null){
            Long oldFileId=existedMain.getFileEntity().getId();
            productImageRepository.delete(existedMain);

            TransactionSynchronizationManager.registerSynchronization(
                    new TransactionSynchronization() {
                        @Override
                        public void afterCommit() {
                           fileService.removeFile(oldFileId);
                        }
                    }
            );
        }
        productImageRepository.save(main);
    }
    @Transactional
    public void deleteProductImages(Long id, List<Long> imageIds){
        if(id==null)
            throw new IllegalArgumentException("Id is null");

        Product product=productRepository.findById(id)
                .orElseThrow(()->new NotFoundException(Product.class.getSimpleName(),id));

        Set<Long> deleteFileIds = new HashSet<>();

        product.getProductImages().removeIf(img -> {
            if (img.getImageType() == ImageType.SECONDARY && imageIds.contains(img.getId())) {
                deleteFileIds.add(img.getFileEntity().getId());
                return true;
            }
            return false;
        });


        productRepository.save(product);

        TransactionSynchronizationManager
                .registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                deleteFileIds.forEach(fileService::removeFile);
            }
        });
    }

    @Transactional void addProductImages(Long id, List<Long> fileIds){
        if(id==null)
            throw new IllegalArgumentException("Id is null");

        Product product=productRepository.findById(id)
                .orElseThrow(()->new NotFoundException(Product.class.getSimpleName(),id));

        List<FileEntity> fileEntities = fileService.getFileEntities(fileIds);

        fileEntities.forEach(fileService::validateFileInUseStatus);

        product.getProductImages().addAll(fileEntities.stream()
                .map(fileEntity->createProductImage(fileEntity.getId(), ImageType.SECONDARY, product))
                .toList());
        productRepository.save(product);

    }
}
