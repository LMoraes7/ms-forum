package br.com.forum.web.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class LoginRequest {

    @NotBlank @Email @Size(min = 1, max = 255)
    private String email;
    @NotBlank @Size(min = 1, max = 255)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Authentication toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
