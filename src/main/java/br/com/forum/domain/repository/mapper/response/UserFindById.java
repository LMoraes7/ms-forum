package br.com.forum.domain.repository.mapper.response;

import java.util.Objects;

public final class UserFindById {

    private final String id;
    private final String name;
    private final String email;
    private final String profilePicture;

    public UserFindById(String id, String name, String email, String profilePicture) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profilePicture = profilePicture;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String email() {
        return email;
    }

    public String profilePicture() {
        return profilePicture;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, profilePicture);
    }

}
