package br.com.forum.web.controller.request;

import br.com.forum.domain.model.Topic;
import br.com.forum.domain.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class TopicRequest {

    @NotBlank @Size(min = 1, max = 255)
    private String title;
    @NotBlank @Size(min = 1, max = 255)
    private String content;

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

    public Topic toDomain(final User user, final String id) {
        return new Topic(id, title, content, LocalDateTime.now(), LocalDateTime.now(), user, List.of());
    }
}
