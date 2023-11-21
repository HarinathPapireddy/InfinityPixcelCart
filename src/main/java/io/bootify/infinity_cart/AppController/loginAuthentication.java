package io.bootify.infinity_cart.AppController;

import io.bootify.infinity_cart.config.PasswordEncoder;
import io.bootify.infinity_cart.domain.User;
import io.bootify.infinity_cart.model.AuthTokenResponse;
import io.bootify.infinity_cart.model.Userlog;
import io.bootify.infinity_cart.repos.UserRepository;
import io.bootify.infinity_cart.service.JwtService;
import io.bootify.infinity_cart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/userValidation")
public class loginAuthentication {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<AuthTokenResponse> login(@RequestBody Userlog userlog){
        User user = userRepository.findByUsername(userlog.getUsername());
        if(user == null) return ResponseEntity.notFound().build();
        if(PasswordEncoder.getPasswordEncoder().matches(userlog.getPassword(), user.getPassword())){
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userlog.getUsername(), userlog.getPassword()));
            String token = jwtService.generateToken(user);
            AuthTokenResponse auth = new AuthTokenResponse();
            auth.setToken(token);
            return ResponseEntity.ok(auth);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }
}
