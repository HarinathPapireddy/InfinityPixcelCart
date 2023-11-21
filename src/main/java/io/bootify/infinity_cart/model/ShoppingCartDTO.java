package io.bootify.infinity_cart.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ShoppingCartDTO {

    private Long id;
    private ProductDTO product;
    private Long quantity;

}
