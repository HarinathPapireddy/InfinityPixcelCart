package io.bootify.infinity_cart.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
    private static BCryptPasswordEncoder encoder;

    private PasswordEncoder(){
        encoder = new BCryptPasswordEncoder();
    }

    public static synchronized BCryptPasswordEncoder getPasswordEncoder(){
        if(encoder==null){
            new PasswordEncoder();
        }
        return encoder;
    }
}
