package com.example.grpcclient.service;

import com.example.grpcclient.dto.EventDTO;
import com.example.grpcclient.entity.Event;
import com.example.grpcclient.entity.Wallet;
import com.example.grpcclient.exception.AppException;
import com.example.grpcclient.repository.EventRepository;
import com.example.grpcclient.repository.WalletRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    EventRepository eventRepository;
    WalletRepository walletRepository;
    ModelMapper modelMapper;

    @Override
    public EventDTO addEvent(EventDTO eventDTO) {
        Wallet wallet = walletRepository.findWalletById(eventDTO.getWalletId()).orElseThrow(() -> new AppException("not found"));
        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setWallet(wallet);
        event = eventRepository.save(event);
        return modelMapper.map(event, EventDTO.class);
    }

    @Override
    public List<EventDTO> getAllEvents(Long walletId) {
        List<Event> events = eventRepository.findEventsByWallet_Id(walletId);
        return events.stream().map(e -> modelMapper.map(e, EventDTO.class)).collect(Collectors.toList());
    }
}
