package com.example.grpcclient.controller;

import com.example.grpcclient.dto.ApiResponse;
import com.example.grpcclient.dto.TransactionRequest;
import com.example.grpcclient.dto.TransactionResp;
import com.example.grpcclient.repository.TransactionRepository;
import com.example.grpcclient.repository.WalletRepository;
import com.example.grpcclient.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
@AllArgsConstructor
public class TransactionController {
    TransactionService transactionService;
    TransactionRepository transactionRepository;
    WalletRepository walletRepository;

    @PostMapping(path = "/transaction")
    public ResponseEntity<TransactionResp> addTransaction(@RequestBody TransactionRequest transactionRequest) {
        TransactionResp res = transactionService.addTransactionByWalletId(transactionRequest);
        if (res == null) {
            return new ResponseEntity(new ApiResponse(400, "Could not create transaction"),
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body(res);
    }

    @GetMapping(path = "/transaction/wallet/{walletId}")
    public ResponseEntity<List<TransactionResp>> getTransactionByWalletId(@PathVariable Long walletId) {
        return ResponseEntity.ok().body(transactionService.getTransactionByWalletId(walletId));
    }

    @GetMapping(path = "transaction/wallet/{walletId}/{startDate}/{endDate}")
    public ResponseEntity<List<TransactionResp>> getTransactionByRangeDate(@PathVariable Long walletId,
                                                                           @PathVariable Date startDate,
                                                                           @PathVariable Date endDate) {
        if (!walletRepository.existsWalletById(walletId)) {
            return new ResponseEntity(new ApiResponse(404, "not found"),
                    HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(transactionService.getTransactionByRangeDate(walletId, startDate, endDate));
    }

}
