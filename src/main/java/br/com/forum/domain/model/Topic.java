package br.com.forum.domain.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record Topic(
        String id,
        String title,
        String contents,
        LocalDateTime creationDate,
        LocalDateTime updateDate,
        User user,
        List<Comment> comments
) {

    @Override
    public List<Comment> comments() {
        return Collections.unmodifiableList(comments);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return Objects.equals(id, topic.id) && Objects.equals(title, topic.title) && Objects.equals(contents, topic.contents) && Objects.equals(creationDate, topic.creationDate) && Objects.equals(updateDate, topic.updateDate) && Objects.equals(user, topic.user) && Objects.equals(comments, topic.comments);
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", user=" + user +
                ", comments=" + comments +
                '}';
    }
}
