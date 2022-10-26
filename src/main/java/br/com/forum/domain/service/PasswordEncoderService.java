package br.com.forum.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public final class PasswordEncoderService {

    private static final Logger logger = LoggerFactory.getLogger(PasswordEncoderService.class);

    private final PasswordEncoder passwordEncoder;

    public PasswordEncoderService(@Qualifier("bCryptPasswordEncoder") final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encrypt(final String password) {
        logger.info("starting new password encryption");
        final var encode = this.passwordEncoder.encode(password);
        logger.info("finalizing new password encryption");
        return encode;
    }
}
