package br.com.forum.web.controller.response;

import br.com.forum.domain.model.Comment;
import br.com.forum.domain.model.Topic;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class CommentCreateResponse {

    public static CommentCreateResponse toResponse(final Comment comment) {
        return new CommentCreateResponse(comment.id(), comment.contents());
    }

    private String id;
    private String content;

    public CommentCreateResponse(final String id, final String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
