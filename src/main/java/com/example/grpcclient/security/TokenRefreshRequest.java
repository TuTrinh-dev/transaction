package com.example.grpcclient.security;


import javax.validation.constraints.NotBlank;

public class TokenRefreshRequest {
    @NotBlank(message = "Token is required")
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
