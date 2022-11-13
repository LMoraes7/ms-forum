package br.com.forum.domain.repository.mapper.response;

import java.util.Objects;

public final class TopicFindById {

    private final String id;

    public TopicFindById(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicFindById that = (TopicFindById) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TopicFindById{" +
                "id='" + id + '\'' +
                '}';
    }

}
