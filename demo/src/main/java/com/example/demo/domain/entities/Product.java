package com.example.demo.domain.entities;

import com.example.demo.domain.entities.common.BaseAccountableEntity;
import com.example.demo.domain.valueObjects.Money;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="products")
@Entity
public class Product extends BaseAccountableEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "sku")
    private String sku;

    @Column(name = "description")
    private String description;


    @Embedded
    @AttributeOverrides(
            {
                    @AttributeOverride(name = "amount", column = @Column(name = "price_amount")),
                    @AttributeOverride(name = "currency", column = @Column(name = "price_currency"))
            }
    )
    private Money money;

    //relational
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> productImages;


}
