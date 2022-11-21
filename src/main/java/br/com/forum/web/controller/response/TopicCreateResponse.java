package br.com.forum.web.controller.response;

import br.com.forum.domain.model.Topic;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class TopicCreateResponse {

    public static TopicCreateResponse toResponse(final Topic topic) {
        return new TopicCreateResponse(topic.id(), topic.title(), topic.contents());
    }

    private String id;
    private String title;
    private String content;

    public TopicCreateResponse(final String id, final String title, final String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
