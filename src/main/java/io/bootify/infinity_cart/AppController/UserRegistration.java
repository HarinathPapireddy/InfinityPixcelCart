package io.bootify.infinity_cart.AppController;

import io.bootify.infinity_cart.model.UserDTO;
import io.bootify.infinity_cart.repos.UserRepository;
import io.bootify.infinity_cart.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userRegistration")
public class UserRegistration {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;



    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createUser(@RequestBody final UserDTO userDTO) {
        if(userRepository.existsByUsernameIgnoreCase(userDTO.getUsername()) && userRepository.existsByEmailIgnoreCase(userDTO.getEmail())){
            return ResponseEntity.status(HttpStatusCode.valueOf(302)).build();
        }else{
            final Long createdId = userService.create(userDTO);
            return ResponseEntity.ok().build();
        }
    }

}
