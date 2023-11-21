package io.bootify.infinity_cart.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private Long id;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String username;

    @Size(max = 255)
    private String password;

    @Size(max = 255)
    private String role;

    @Size(max = 255)
    private String gender;

    @Size(max = 255)
    private String phone;

    @Size(max = 255)
    private String email;

}
