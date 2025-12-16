package shop.API.core.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.API.core.domain.entities.common.BaseAccountableEntity;
import shop.API.core.domain.enums.ImageType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="product_images")
@Entity
public class ProductImage extends BaseAccountableEntity {

    @Column(name = "image")
    private String image;

    @Enumerated(EnumType.STRING)
    @Column(name = "image_type")
    private ImageType imageType;

    //relational

    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;


}
