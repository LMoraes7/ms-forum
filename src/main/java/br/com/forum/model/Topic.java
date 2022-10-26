package br.com.forum.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Topic {

    private final String id;
    private final String title;
    private final LocalDateTime creationDate;
    private final LocalDateTime updateDate;
    private final User user;

    private List<Comment> comments;

    public Topic(String id, String title, LocalDateTime creationDate, LocalDateTime updateDate, User user) {
        this.id = id;
        this.title = title;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.user = user;
    }

    public Topic(String id, String title, LocalDateTime creationDate, LocalDateTime updateDate, User user, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.user = user;
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public User getUser() {
        return user;
    }

    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return Objects.equals(id, topic.id) && Objects.equals(title, topic.title) && Objects.equals(creationDate, topic.creationDate) && Objects.equals(updateDate, topic.updateDate) && Objects.equals(user, topic.user) && Objects.equals(comments, topic.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, creationDate, updateDate, user, comments);
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", user=" + user +
                ", comments=" + comments +
                '}';
    }
}
