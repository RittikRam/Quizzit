package com.rittik.MyQuizzApp.dto;

import java.util.List;

public class JwtResponseDTO {
        private String username;
        private String token;
        private String tokenType = "Bearer";
        private List<String> roles;
        private Long id;

    public JwtResponseDTO(String username, String token, List<String> roles, Long id) {
        this.username = username;
        this.token = token;
        this.roles = roles;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
