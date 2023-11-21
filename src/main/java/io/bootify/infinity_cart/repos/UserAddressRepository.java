package io.bootify.infinity_cart.repos;

import io.bootify.infinity_cart.domain.Address;
import io.bootify.infinity_cart.domain.User;
import io.bootify.infinity_cart.domain.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    List<UserAddress> getAllAddressByUser(User user);
    boolean existsByUserAndAddress(User user,Address address);

    UserAddress findByUserAndAddress(User user,Address address);
    int deleteByAddress(Address address);
}
