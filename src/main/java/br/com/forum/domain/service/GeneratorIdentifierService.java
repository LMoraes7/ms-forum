package br.com.forum.domain.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public final class GeneratorIdentifierService {

    public String userId() {
        return "USR-".concat(generateHash());
    }

    public String topicId() {
        return "TOP-".concat(generateHash());
    }

    public String commentId() {
        return "COM-".concat(generateHash());
    }

    private String generateHash() {
        final var split = UUID.randomUUID().toString().split("-");
        return split[1].concat(split[2]);
    }
}
