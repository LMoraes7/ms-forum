package br.com.forum.domain.repository.mapper.response;

import java.util.Objects;

public record UserFindById(String id, String name, String email, String profilePicture) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFindById that = (UserFindById) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(profilePicture, that.profilePicture);
    }

    @Override
    public String toString() {
        return "UserFindById{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                '}';
    }
}
