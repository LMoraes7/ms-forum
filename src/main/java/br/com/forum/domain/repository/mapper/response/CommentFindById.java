package br.com.forum.domain.repository.mapper.response;

import java.time.LocalDateTime;
import java.util.Objects;

public record CommentFindById(
        String id,
        String contents,
        LocalDateTime creationDate,
        LocalDateTime updateDate,
        br.com.forum.domain.repository.mapper.response.CommentFindById.User user
) {

    public record User(String name, String profilePicture) {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CommentFindById.User user = (CommentFindById.User) o;
            return Objects.equals(name, user.name) && Objects.equals(profilePicture, user.profilePicture);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentFindById that = (CommentFindById) o;
        return Objects.equals(id, that.id) && Objects.equals(contents, that.contents) && Objects.equals(creationDate, that.creationDate) && Objects.equals(updateDate, that.updateDate) && Objects.equals(user, that.user);
    }

    @Override
    public String toString() {
        return "CommentFindById{" +
                "id='" + id + '\'' +
                ", contents='" + contents + '\'' +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", user=" + user +
                '}';
    }
}
