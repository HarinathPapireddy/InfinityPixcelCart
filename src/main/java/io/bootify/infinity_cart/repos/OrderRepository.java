package io.bootify.infinity_cart.repos;

import io.bootify.infinity_cart.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {
}
