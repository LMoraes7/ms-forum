package br.com.forum.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    @Bean("bCryptPasswordEncoder")
    public PasswordEncoder passwordEncoderConfig() {
        return new BCryptPasswordEncoder();
    }
}