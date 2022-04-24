package com.example.grpcclient.repository;

import com.example.grpcclient.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsEventByNameAndWalletId(String name, Long walletId);

    Event findEventByIdAndWalletId(Long id, Long walletId);

    List<Event> findEventsByWallet_Id(Long walletId);
}
