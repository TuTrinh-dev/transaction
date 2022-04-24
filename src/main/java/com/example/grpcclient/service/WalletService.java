package com.example.grpcclient.service;

import com.example.grpcclient.dto.WalletDTO;
import com.example.grpcclient.entity.Wallet;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WalletService {
    WalletDTO createWallet(WalletDTO walletDTO);

    List<WalletDTO> getWalletCurrentUser();

    List<WalletDTO> getWalletByUserId();

    Wallet findWalletByWalletId(Long walletId);

    Double getCurrentBalance(Long walletId);

    void updateCurrentBalance(Long walletId, Long groupId, Double amount);

    WalletDTO updateWallet(WalletDTO walletDTO);

    void deleteWallet(Long walletId);

    Page<WalletDTO> getAllWalletPagination(int page, int size, String sort);
}
