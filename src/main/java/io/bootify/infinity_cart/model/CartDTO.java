package io.bootify.infinity_cart.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartDTO {
    private Long id;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String description;

    @Size(max = 255)
    private String price;

    @Size(max = 255)
    private String ratings;

    @Size(max = 255)
    private String image;

    private Long productCategory;

}
