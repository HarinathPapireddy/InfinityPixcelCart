package io.bootify.infinity_cart.repos;

import io.bootify.infinity_cart.domain.Image;
import io.bootify.infinity_cart.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {

    Image getImageByProductAndIsClickBait(Product product,boolean isClickBait);

    List<Image> getImagesByProduct(Product product);
    boolean existsImageByProductAndIsClickBait(Product product,boolean isClickBait);
}
