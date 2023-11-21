package io.bootify.infinity_cart.repos;

import io.bootify.infinity_cart.domain.Product;
import io.bootify.infinity_cart.domain.User;
import io.bootify.infinity_cart.domain.WishlistCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface WishlistCartRepository extends JpaRepository<WishlistCart, Long> {

    List<WishlistCart> getAllProductsByUser(User user);

    WishlistCart getByUserAndProduct(User user,Product product);
    boolean existsByUserAndProduct(User user,Product product);
}
