package com.example.grpcclient.service;

import com.example.grpcclient.dto.EventDTO;

import java.util.List;

public interface EventService {
    EventDTO addEvent(EventDTO eventDTO);

    List<EventDTO> getAllEvents(Long walletId);
}
