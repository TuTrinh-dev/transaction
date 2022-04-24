package com.example.grpcclient.service;

import com.example.grpcclient.dto.TransactionRequest;
import com.example.grpcclient.dto.TransactionResp;

import java.sql.Date;
import java.util.List;

public interface TransactionService {
    TransactionResp addTransactionByWalletId(TransactionRequest transactionRequest);

    List<TransactionResp> getTransactionByWalletId(Long walletId);

    List<TransactionResp> getTransactionByRangeDate(Long walletId, Date startDate, Date endDate);
}
