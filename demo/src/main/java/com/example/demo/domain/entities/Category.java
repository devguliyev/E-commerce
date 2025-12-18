package com.example.demo.domain.entities;

import com.example.demo.domain.entities.common.BaseAccountableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="categories")
@Entity
public class Category extends BaseAccountableEntity {

    @Column(name="name")
    private String name;
    //relational
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
