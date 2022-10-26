package br.com.forum.domain.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public final class GeneratorIdentifierService {

    private final String USER_PREFIX = "USR-";
    private final String TOPIC_PREFIX = "TOP-";
    private final String COMMENT_PREFIX = "COM-";

    public String userId() {
        final String[] split = generateArrayUuid();
        return USER_PREFIX.concat(split[1]).concat(split[2]);
    }

    public String topicId() {
        final String[] split = generateArrayUuid();
        return TOPIC_PREFIX.concat(split[1]).concat(split[2]);
    }

    public String commentId() {
        final String[] split = generateArrayUuid();
        return COMMENT_PREFIX.concat(split[1]).concat(split[2]);
    }

    private static String[] generateArrayUuid() {
        return UUID.randomUUID().toString().split("-");
    }
}
