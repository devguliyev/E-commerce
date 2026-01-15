package com.example.demo.unit;

import com.example.demo.domain.entities.Category;
import com.example.demo.domain.entities.FileEntity;
import com.example.demo.domain.entities.Product;
import com.example.demo.domain.enums.Currency;
import com.example.demo.domain.enums.FileStatus;
import com.example.demo.domain.enums.ImageType;
import com.example.demo.domain.valueObjects.Money;
import com.example.demo.dto.products.PostProductDto;
import com.example.demo.mapperProfiles.ProductMapper;
import com.example.demo.repository.ProductImageRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.implementations.ProductServiceImpl;
import com.example.demo.service.interfaces.CategoryService;
import com.example.demo.service.interfaces.FileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    ProductRepository productRepository;
    @Mock
    CategoryService categoryService;
    @Mock
    FileService fileService;
    @Mock
    ProductMapper productMapper;
    @Mock
    ProductImageRepository productImageRepository;
    @InjectMocks
    ProductServiceImpl productServiceImpl;

    @Test
    public void shouldCreateProduct(){
        //GIVEN
        PostProductDto dto = new PostProductDto(
                "Phone",
                "CH128",
                "Test",
                BigDecimal.valueOf(10),
                Currency.AZN,
                1L,// categoryId
                1L,              // primaryImageId
                List.of(2L, 3L) // secondary image ids
        );
        when(productRepository.existsByName(dto.name())).thenReturn(false);



        Product product=new Product();
        product.setName(dto.name());
        product.setSku(dto.sku());
        product.setDescription(dto.description());
        product.setMoney(new Money(dto.priceAmount(),dto.currency()));

        when(productMapper.toEntity(dto)).thenReturn(product);


        Category category=new Category();
        category.setId(dto.categoryId());
        category.setName("TestCategory");

        when(categoryService.getCategory(1L)).thenReturn(category);

        FileEntity fileEntity=new FileEntity();
        fileEntity.setId(dto.primaryImageId());
        fileEntity.setStatus(FileStatus.TEMP);

        FileEntity fileEntity2=new FileEntity();
        fileEntity2.setId(dto.imageIds().getFirst());
        fileEntity2.setStatus(FileStatus.TEMP);

        FileEntity fileEntity3=new FileEntity();
        fileEntity3.setId(dto.imageIds().getLast());
        fileEntity3.setStatus(FileStatus.TEMP);

        when(fileService.getFileEntity(1L)).thenReturn(fileEntity);
        when(fileService.getFileEntity(2L)).thenReturn(fileEntity2);
        when(fileService.getFileEntity(3L)).thenReturn(fileEntity3);

        //WHEN

        productServiceImpl.create(dto);

        //THEN
        ArgumentCaptor<Product> captor=ArgumentCaptor.forClass(Product.class);

        verify(productRepository).existsByName(dto.name());
        verify(productRepository).save(captor.capture());

        verify(fileService).changeStatus(fileEntity,FileStatus.USED);
        verify(fileService).changeStatus(fileEntity2,FileStatus.USED);
        verify(fileService).changeStatus(fileEntity3,FileStatus.USED);

        Product saved=captor.getValue();

        assertEquals("Phone",saved.getName());
        assertEquals(category,saved.getCategory());
        assertEquals(3,saved.getProductImages().size());
        assertEquals(1,saved
                .getProductImages()
                .stream()
                .filter(img->img.getImageType()== ImageType.PRIMARY)
                .count());
        assertNotNull(saved.getCreatedAt());

    }
}
