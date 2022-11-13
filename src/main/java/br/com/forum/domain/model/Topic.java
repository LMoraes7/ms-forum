package br.com.forum.domain.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Topic {

    private final String id;
    private final String title;
    private final String contents;
    private final LocalDateTime creationDate;
    private final LocalDateTime updateDate;
    private final User user;
    private final List<Comment> comments;

    public Topic(
            String id,
            String title,
            String contents,
            LocalDateTime creationDate,
            LocalDateTime updateDate,
            User user,
            List<Comment> comments
    ) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.user = user;
        this.comments = comments;
    }

    public String id() {
        return id;
    }

    public String title() {
        return title;
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

    public User user() {
        return user;
    }

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
    public int hashCode() {
        return Objects.hash(id, title, contents, creationDate, updateDate, user, comments);
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