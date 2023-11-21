package io.bootify.infinity_cart.repos;

import io.bootify.infinity_cart.domain.Product;
import io.bootify.infinity_cart.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> getProductsByName(String name);

    List<Product> getProductByProductCategory(ProductCategory category);

}
