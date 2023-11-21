package io.bootify.infinity_cart.repos;

import io.bootify.infinity_cart.domain.Payement;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PayementRepository extends JpaRepository<Payement, Long> {
}
