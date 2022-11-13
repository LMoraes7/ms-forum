package br.com.forum.web.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class LoginResponse {

    private String mode;
    private String token;

    public LoginResponse(final String mode, final String token) {
        this.mode = mode;
        this.token = token;
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
}
