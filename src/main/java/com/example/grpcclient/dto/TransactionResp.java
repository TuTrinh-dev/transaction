package com.example.grpcclient.dto;

import com.example.grpcclient.entity.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResp implements Serializable {
    private Double amount;
    private CategoryDTO category;
    private String note;
    private Date date;
    private long walletId;
    private Person person;
    private String location;
    private EventDTO event;
}
