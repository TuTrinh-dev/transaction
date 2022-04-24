package com.example.grpcclient.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class UserResp {
    private static final long serialVersionUID = 1L;
    private long id;
    @NonNull
    private String username;
    @NonNull
    private String displayName;
    @NonNull
    private String role;

}
