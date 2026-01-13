package com.example.demo.service.implementations;

import com.example.demo.domain.entities.Category;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService
{
    private final CategoryRepository categoryRepository;
    public Category getCategory(Long id){
        if(id == null)
            throw new IllegalArgumentException("Category Id is null");

        Category category=categoryRepository.findById(id).orElseThrow(
                ()-> new NotFoundException(Category.class.getSimpleName(),id)
        );
        return category;
    }
}
