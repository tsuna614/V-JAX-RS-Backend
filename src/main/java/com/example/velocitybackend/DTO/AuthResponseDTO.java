package com.example.velocitybackend.DTO;

import com.example.velocitybackend.models.UserModel;

public class AuthResponseDTO {
    private String message;
    private String accessToken;
    private String refreshToken;
    private UserModel user;

    public AuthResponseDTO() {}

    public AuthResponseDTO(String message, String accessToken, String refreshToken, UserModel user) {
        this.message = message;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
