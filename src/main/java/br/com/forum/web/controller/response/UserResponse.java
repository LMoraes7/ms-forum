package br.com.forum.web.controller.response;

import br.com.forum.domain.repository.mapper.response.UserFindById;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class UserResponse {

    public static UserResponse toResponse(final UserFindById userFindById) {
        return new UserResponse(userFindById.id(), userFindById.name(), userFindById.email(), userFindById.profilePicture());
    }

    private String id;
    private String name;
    private String email;
    private String image;

    public UserResponse(final String id, final String name, final String email, final String image) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
