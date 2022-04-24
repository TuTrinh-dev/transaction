package com.example.grpcclient.controller;

import com.example.grpcclient.dto.ApiResponse;
import com.example.grpcclient.dto.EventDTO;
import com.example.grpcclient.repository.EventRepository;
import com.example.grpcclient.repository.WalletRepository;
import com.example.grpcclient.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
@AllArgsConstructor
public class EventController {

    EventRepository eventRepository;
    EventService eventService;
    WalletRepository walletRepository;

    @PostMapping(path = "/event")
    public ResponseEntity<EventDTO> addEventByWalletId(@RequestBody EventDTO eventDTO) {
        if (eventRepository.existsEventByNameAndWalletId(eventDTO.getName(), eventDTO.getWalletId())) {
            return new ResponseEntity(new ApiResponse(400, "Event is already exist!"),
                    HttpStatus.BAD_REQUEST);
        }
        if (!walletRepository.existsById(eventDTO.getWalletId())) {
            return new ResponseEntity(new ApiResponse(400, "Wallet is doesn't exist!"),
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body(eventService.addEvent(eventDTO));
    }

    @GetMapping(path = "/event/{walletId}")
    public ResponseEntity<List<EventDTO>> getAllEvents(@Valid @PathVariable long walletId) {
        return ResponseEntity.ok().body(eventService.getAllEvents(walletId));
    }
}
