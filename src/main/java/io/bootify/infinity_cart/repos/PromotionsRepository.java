package io.bootify.infinity_cart.repos;

import io.bootify.infinity_cart.domain.Promotions;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PromotionsRepository extends JpaRepository<Promotions, Long> {
}
