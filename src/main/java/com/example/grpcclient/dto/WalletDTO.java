package com.example.grpcclient.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDTO implements Serializable {
    private long id;
    private String name;
    private String currency;
    private Double currentBalance;
    private String username;
}
