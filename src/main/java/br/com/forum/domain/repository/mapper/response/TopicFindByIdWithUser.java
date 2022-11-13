package br.com.forum.domain.repository.mapper.response;

import java.time.LocalDateTime;
import java.util.Objects;

public final class TopicFindByIdWithUser {

    private final String id;
    private final String title;
    private final String contents;
    private final LocalDateTime creationDate;
    private final LocalDateTime updateDate;
    private final User user;

    public TopicFindByIdWithUser(
            String id,
            String title,
            String contents,
            LocalDateTime creationDate,
            LocalDateTime updateDate,
            User user
    ) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.user = user;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicFindByIdWithUser that = (TopicFindByIdWithUser) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(contents, that.contents) && Objects.equals(creationDate, that.creationDate) && Objects.equals(updateDate, that.updateDate) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, contents, creationDate, updateDate, user);
    }

    @Override
    public String toString() {
        return "TopicFindByIdWithUser[" +
                "id=" + id + ", " +
                "title=" + title + ", " +
                "contents=" + contents + ", " +
                "creationDate=" + creationDate + ", " +
                "updateDate=" + updateDate + ", " +
                "user=" + user + ']';
    }

    public static final class User {

        private final String name;
        private final String profilePicture;

        public User(String name, String profilePicture) {
            this.name = name;
            this.profilePicture = profilePicture;
        }


        public String name() {
            return name;
        }

        public String profilePicture() {
            return profilePicture;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return Objects.equals(name, user.name) && Objects.equals(profilePicture, user.profilePicture);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, profilePicture);
        }

        @Override
        public String toString() {
            return "User[" +
                    "name=" + name + ", " +
                    "profilePicture=" + profilePicture + ']';
        }

    }
}
