package com.example.grpcclient.security;

import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
@ConfigurationProperties("spring")
@Getter
@Setter
public class JwtTokenProvider {
    private String JWT_SECRET;
    private int expiredTime;


    public String generateToken(CustomUserDetail userDetails) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expiredTime);
        String role = userDetails.getUser().getRoles().stream().findFirst().get().getName();
        return Jwts.builder()
                .claim("userId", userDetails.getUser().getId())
                .claim("name", userDetails.getUsername())
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("name").toString();
    }

    public String getUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.get("userId").toString();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }
}
