package shop.API.core.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.API.core.domain.entities.common.BaseAccountableEntity;
import shop.API.core.domain.entities.common.BaseEntity;
import shop.API.core.domain.valueObjects.Money;

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

    @Column(name = "money")
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

    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImages;


}
