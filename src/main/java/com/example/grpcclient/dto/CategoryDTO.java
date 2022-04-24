package com.example.grpcclient.dto;

import lombok.Data;

@Data
public class CategoryDTO {
    private long id;
    private String name;
    private long transactionId;
    private long groupId;
}
