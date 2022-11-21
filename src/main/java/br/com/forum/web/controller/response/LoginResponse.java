package br.com.forum.web.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class LoginResponse {

    private String mode;
    private String token;
    private String name;
    private String profilePicture;

    public LoginResponse(final String mode, final String token, final String name, final String profilePicture) {
        this.mode = mode;
        this.token = token;
        this.name = name;
        this.profilePicture = profilePicture;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
