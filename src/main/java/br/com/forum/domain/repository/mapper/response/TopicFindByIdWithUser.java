package br.com.forum.domain.repository.mapper.response;

import java.time.LocalDateTime;
import java.util.Objects;

public record TopicFindByIdWithUser(
        String id,
        String title,
        String contents,
        LocalDateTime creationDate,
        LocalDateTime updateDate,
        TopicFindByIdWithUser.User user
) {

    public record User(String name, String profilePicture) {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return Objects.equals(name, user.name) && Objects.equals(profilePicture, user.profilePicture);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicFindByIdWithUser that = (TopicFindByIdWithUser) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(contents, that.contents) && Objects.equals(creationDate, that.creationDate) && Objects.equals(updateDate, that.updateDate) && Objects.equals(user, that.user);
    }

}
