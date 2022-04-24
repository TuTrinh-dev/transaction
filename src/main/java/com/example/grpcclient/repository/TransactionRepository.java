package com.example.grpcclient.repository;

import com.example.grpcclient.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findTransactionsByWalletId(Long walletId);

    @Query(value = "select * from transaction where wallet_id = ?1 and date between ?2 and ?3", nativeQuery = true)
    List<Transaction> getTransactionsByRangeDate(Long walletId, Date startDate, Date endDate);
}
