package io.bootify.infinity_cart.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductCategoryDTO {

    private Long id;

    @Size(max = 255)
    private String categoryName;

    private Long parentProductCategoryId;

}
