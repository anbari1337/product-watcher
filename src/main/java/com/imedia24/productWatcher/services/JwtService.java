package com.imedia24.productWatcher.services;

import com.imedia24.productWatcher.config.JwtConfig;
import com.imedia24.productWatcher.core.constant.Constant;
import com.imedia24.productWatcher.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@AllArgsConstructor
@Service
public class JwtService {

    private final JwtConfig jwtConfig;

    public String generateAccessToken(User user) {
        return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }

    private String generateToken(User user, int expirationDate) {
        Claims claims = Jwts.claims()
                .subject(user.getId().toString())
                .add(Map.of("name", user.getFullName()))
                .add(Map.of("email", user.getEmail()))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + Constant.ONE_SECOND_IN_MILLIS * expirationDate))
                .build();
        return new Jwt(claims, jwtConfig.getSecretKey()).toString();

    }

    public Jwt parse(String token) {
        Claims claims =  Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return new Jwt(claims, jwtConfig.getSecretKey());
    }


}
