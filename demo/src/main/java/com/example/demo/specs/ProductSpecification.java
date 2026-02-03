package com.example.demo.specs;

import com.example.demo.domain.entities.Product;
import com.example.demo.dto.products.ProductQueryDto;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class ProductSpecification {
    public static Specification<Product> filterBy(ProductQueryDto queryDto) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (queryDto.search() != null && !queryDto.search().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + queryDto.search().toLowerCase() + "%"));
            }

            if (queryDto.categoryId() != null) {
                predicates.add(cb.equal(root.get("category").get("id"), queryDto.categoryId()));
            }

            if (queryDto.minPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), queryDto.minPrice()));
            }

            if (queryDto.maxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), queryDto.maxPrice()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
