package com.example.demo.domain.entities;

import com.example.demo.domain.entities.common.BaseAccountableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="colors")
@Entity
public class Color extends BaseAccountableEntity {

    @Column(name="name")
    private String name;

//    private List<Product> products;
}
