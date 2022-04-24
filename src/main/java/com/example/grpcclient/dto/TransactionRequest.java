package com.example.grpcclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class TransactionRequest {
    private Double amount;
    private long categoryId;
    private String note;
    private Date date;
    private long walletId;
    private long person_id;
    private String location;
    private long eventId;
}
