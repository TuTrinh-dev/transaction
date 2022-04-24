package com.example.grpcclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class AuthResponse {
    @NonNull
    private String access_token;
    private String refreshToken;
}
