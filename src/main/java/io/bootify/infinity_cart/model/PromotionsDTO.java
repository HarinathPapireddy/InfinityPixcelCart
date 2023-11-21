package io.bootify.infinity_cart.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PromotionsDTO {

    private Long id;

    @Size(max = 255)
    private String image;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String description;

    @Size(max = 255)
    private String dicount;

    private LocalDate startDate;

    private LocalDate endDate;

}
