package br.com.forum.web.controller.request;

import br.com.forum.domain.model.Topic;
import br.com.forum.domain.model.User;

import java.time.LocalDateTime;
import java.util.List;

public final class TopicRequest {

    private final String title;
    private final String content;

    public TopicRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Topic toDomain(final User user, final String id) {
        return new Topic(id, title, content, LocalDateTime.now(), LocalDateTime.now(), user, List.of());
    }
}
