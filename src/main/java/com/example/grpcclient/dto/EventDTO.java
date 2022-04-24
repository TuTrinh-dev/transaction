package com.example.grpcclient.dto;

import lombok.Data;

@Data
public class EventDTO {
    private long id;
    private String name;
    private long transactionId;
    private long walletId;
}
