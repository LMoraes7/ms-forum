package br.com.forum.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public final class Comment {

    private final String id;
    private final String contents;
    private final LocalDateTime creationDate;
    private final LocalDateTime updateDate;
    private final Topic topic;
    private final User user;

    public Comment(
            String id,
            String contents,
            LocalDateTime creationDate,
            LocalDateTime updateDate,
            Topic topic,
            User user
    ) {
        this.id = id;
        this.contents = contents;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.topic = topic;
        this.user = user;
    }

    public String id() {
        return id;
    }

    public String contents() {
        return contents;
    }

    public LocalDateTime creationDate() {
        return creationDate;
    }

    public LocalDateTime updateDate() {
        return updateDate;
    }

    public Topic topic() {
        return topic;
    }

    public User user() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) && Objects.equals(contents, comment.contents) && Objects.equals(creationDate, comment.creationDate) && Objects.equals(updateDate, comment.updateDate) && Objects.equals(topic, comment.topic) && Objects.equals(user, comment.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contents, creationDate, updateDate, topic, user);
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