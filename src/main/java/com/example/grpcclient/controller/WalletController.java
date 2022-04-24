package com.example.grpcclient.controller;

import com.example.grpcclient.dto.ApiResponse;
import com.example.grpcclient.dto.WalletDTO;
import com.example.grpcclient.repository.WalletRepository;
import com.example.grpcclient.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
@AllArgsConstructor
public class WalletController {

    WalletService walletService;
    WalletRepository walletRepository;

    @PostMapping(path = "/wallet")
    public ResponseEntity<?> createWallet(@RequestBody WalletDTO walletDTO) {
        walletDTO = walletService.createWallet(walletDTO);
        return ResponseEntity.ok().body(walletDTO);
    }

    @GetMapping(path = "/wallet")
    public ResponseEntity<List<WalletDTO>> getWalletCurrentUser() {
        List<WalletDTO> walletDTOs = walletService.getWalletCurrentUser();
        return ResponseEntity.ok().body(walletDTOs);
    }

    @PutMapping(path = "/wallet")
    public ResponseEntity<WalletDTO> updateWallet(@RequestBody WalletDTO walletDTO) {
        if (!walletRepository.existsWalletById(walletDTO.getId())) {
            return new ResponseEntity(new ApiResponse(404, "Not found"),
                    HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(walletService.updateWallet(walletDTO));
    }

    @DeleteMapping(path = "/wallet/{walletId}")
    public ResponseEntity<?> deleteWallet(@PathVariable Long walletId) {
        if (!walletRepository.existsWalletById(walletId)) {
            return new ResponseEntity(new ApiResponse(404, "Not found"),
                    HttpStatus.NOT_FOUND);
        }
        walletService.deleteWallet(walletId);
        return ResponseEntity.ok().build();
    }
}
