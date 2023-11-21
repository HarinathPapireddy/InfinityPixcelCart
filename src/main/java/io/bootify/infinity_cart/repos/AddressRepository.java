package io.bootify.infinity_cart.repos;

import io.bootify.infinity_cart.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;




public interface AddressRepository extends JpaRepository<Address, Long> {
}
