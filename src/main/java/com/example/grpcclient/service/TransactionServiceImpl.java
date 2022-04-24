package com.example.grpcclient.service;

import com.example.grpcclient.dto.TransactionRequest;
import com.example.grpcclient.dto.TransactionResp;
import com.example.grpcclient.entity.Category;
import com.example.grpcclient.entity.Event;
import com.example.grpcclient.entity.Transaction;
import com.example.grpcclient.entity.Wallet;
import com.example.grpcclient.repository.CategoryRepository;
import com.example.grpcclient.repository.EventRepository;
import com.example.grpcclient.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    TransactionRepository transactionRepository;
    WalletService walletService;
    CategoryRepository categoryRepository;
    EventRepository eventRepository;
    ModelMapper modelMapper;

    @Override
    @Transactional
    public TransactionResp addTransactionByWalletId(TransactionRequest transactionRequest) {
        Long walletId = transactionRequest.getWalletId();
        Wallet wallet = walletService.findWalletByWalletId(walletId);
        Category category = categoryRepository.findById(transactionRequest.getCategoryId()).orElseThrow();
        Long groupId = category.getGroupCategory().getId();
        if (Objects.isNull(category)) {
            return null;
        }
        if (groupId != 5) {
            if (!this.verifyCurrentBalance(walletId, transactionRequest.getAmount())) {
                return null;
            }
        }
        Transaction transaction = new Transaction();

        if (!Objects.isNull(transactionRequest.getEventId())) {
            Event event = eventRepository.findEventByIdAndWalletId(transactionRequest.getEventId(), walletId);
            if (Objects.isNull(event)) {
                return null;
            }
            transaction.setEvent(event);
        }
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setWallet(wallet);
        transaction.setLocation(transactionRequest.getLocation());
        transaction.setNote(transactionRequest.getNote());
        transaction.setPersons(null);
        transaction.setCategory(category);
        transaction.setDate(transactionRequest.getDate());
        transaction = transactionRepository.save(transaction);
        walletService.updateCurrentBalance(walletId, groupId, transactionRequest.getAmount());
        return modelMapper.map(transaction, TransactionResp.class);
    }

    @Override
    public List<TransactionResp> getTransactionByWalletId(Long walletId) {
        List<Transaction> transactions = transactionRepository.findTransactionsByWalletId(walletId);
        return transactions.stream().map(e -> modelMapper.map(e, TransactionResp.class)).collect(Collectors.toList());
    }

    @Override
    public List<TransactionResp> getTransactionByRangeDate(Long walletId, Date startDate, Date endDate) {
        List<Transaction> transactions = transactionRepository.getTransactionsByRangeDate(walletId, startDate, endDate);
        return transactions.stream().map(e -> modelMapper.map(e, TransactionResp.class)).collect(Collectors.toList());
    }

    private boolean verifyCurrentBalance(Long walletId, Double amount) {
        double walletBalance = walletService.getCurrentBalance(walletId);
        if (amount < walletBalance || amount == walletBalance) {
            return true;
        }
        return false;
    }
}
