package com.example.grpcclient.service;

import com.example.grpcclient.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    Optional<RefreshToken> findByToken(String token);

    RefreshToken createRefreshToken(Long userId);

    void deleteByUserId(Long userId);

    RefreshToken verifyExpiration(RefreshToken token);
}
