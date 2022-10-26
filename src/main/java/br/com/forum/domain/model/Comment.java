package br.com.forum.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public record Comment(
        String id,
        String contents,
        LocalDateTime creationDate,
        LocalDateTime updateDate,
        Topic topic,
        User user
) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) && Objects.equals(contents, comment.contents) && Objects.equals(creationDate, comment.creationDate) && Objects.equals(updateDate, comment.updateDate) && Objects.equals(topic, comment.topic) && Objects.equals(user, comment.user);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", contents='" + contents + '\'' +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", topic=" + topic +
                ", user=" + user +
                '}';
    }
}
