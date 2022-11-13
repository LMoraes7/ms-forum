package br.com.forum.web.controller.request;

import br.com.forum.domain.model.Comment;
import br.com.forum.domain.model.Topic;
import br.com.forum.domain.model.User;
import br.com.forum.domain.repository.mapper.response.TopicFindById;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class CommentRequest {

    @NotBlank
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Comment toDomain(final User user, final String id, final TopicFindById topic) {
        return new Comment(id, content, LocalDateTime.now(), LocalDateTime.now(), new Topic(topic.id(), null, null, null, null, null, null), user);
    }
}
