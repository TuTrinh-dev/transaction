package com.example.grpcclient.service;

import com.example.grpcclient.entity.RefreshToken;
import com.example.grpcclient.exception.TokenRefreshException;
import com.example.grpcclient.repository.RefreshTokenRepository;
import com.example.grpcclient.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    RefreshTokenRepository refreshTokenRepository;
    UserRepository userRepository;
    @Value("${jwtRefreshExpiration}")
    private Long refreshTokenDurationMs;

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Transactional
    @Override
    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }
}
