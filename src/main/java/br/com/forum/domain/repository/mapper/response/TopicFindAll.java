package br.com.forum.domain.repository.mapper.response;

import java.time.LocalDateTime;
import java.util.Objects;

public final class TopicFindAll {

    private final String id;
    private final String title;
    private final LocalDateTime creationDate;

    public TopicFindAll(String id, String title, LocalDateTime creationDate) {
        this.id = id;
        this.title = title;
        this.creationDate = creationDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicFindAll that = (TopicFindAll) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, creationDate);
    }

    @Override
    public String toString() {
        return "TopicFindAll{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }
}
