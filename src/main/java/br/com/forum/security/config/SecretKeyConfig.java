package br.com.forum.security.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Configuration
public class SecretKeyConfig {

    @Bean("secretKey")
    public SecretKey securityKeyConfig() {
        return Keys.secretKeyFor(HS256);
    }
}
