package com.example.grpcclient.repository;

import com.example.grpcclient.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Boolean existsWalletById(Long walletId);

    List<Wallet> findWalletsByUser_Id(Long userId);

    Optional<Wallet> findWalletById(Long walletId);
}
