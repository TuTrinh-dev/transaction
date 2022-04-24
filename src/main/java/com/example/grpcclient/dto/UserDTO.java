package com.example.grpcclient.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class UserDTO {
    private long id;
    @NotEmpty(message = "Username is required")
    private String username;
    @NotEmpty(message = "Email is required")
    @Pattern(regexp = "^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$", message = "Email be valid")
    private String email;
    @NotEmpty(message = "Password is required")
    private String password;
    private String role;
    @NotEmpty(message = "Displayname is required")
    private String displayname;
}
