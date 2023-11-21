package io.bootify.infinity_cart.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDTO {

    private Long id;

    private LocalDateTime orderedDate;

    @Size(max = 255)
    private String totalPrice;

    private Long product;

    private Long userAddress;

    private Long payment;

}
