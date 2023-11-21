package io.bootify.infinity_cart.repos;

import io.bootify.infinity_cart.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    ProductCategory getByCategoryName(String categoryName);
}
