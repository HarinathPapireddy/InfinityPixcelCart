package io.bootify.infinity_cart.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AddressDTO {

    private Long id;

    private String name;

    private String phone;

    @Size(max = 255)
    private String line1;

    @Size(max = 255)
    private String line2;

    @Size(max = 255)
    private String city;

    private String state;

    @Size(max = 255)
    private String country;

    @Size(max = 255)
    private String pincode;

    @Size(max = 255)
    private String type;

    private String username;

}
