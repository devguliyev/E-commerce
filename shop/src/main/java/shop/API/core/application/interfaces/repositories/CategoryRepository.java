package shop.API.core.application.interfaces.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.API.core.domain.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
