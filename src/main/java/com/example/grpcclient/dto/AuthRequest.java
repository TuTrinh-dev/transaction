package com.example.grpcclient.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class AuthRequest {
    @NotEmpty(message = "email is required")
    @Pattern(regexp = "^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$", message = "Email be valid")
    private String email;
    @NotNull(message = "password is required")
    private String password;

}
