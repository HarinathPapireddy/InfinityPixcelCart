package io.bootify.infinity_cart.repos;

import io.bootify.infinity_cart.domain.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {
}
