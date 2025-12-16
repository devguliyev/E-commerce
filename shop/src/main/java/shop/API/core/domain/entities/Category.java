package shop.API.core.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.API.core.domain.entities.common.BaseAccountableEntity;

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
