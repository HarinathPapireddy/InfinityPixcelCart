package io.bootify.infinity_cart.repos;

import io.bootify.infinity_cart.domain.Product;
import io.bootify.infinity_cart.domain.ShoppingCart;
import io.bootify.infinity_cart.domain.User;
import io.bootify.infinity_cart.domain.WishlistCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    List<ShoppingCart> getByUser(User user);
    boolean existsByUserAndProduct(User user, Product product);

    ShoppingCart getByUserAndProduct(User user, Product product);
}
