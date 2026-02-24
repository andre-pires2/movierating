package com.andre.movierating.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret_key;

    @Value("${jwt.expiration}")
    private int expiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret_key.getBytes());
    }

    public String generateToken(String username) {

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {

        Jws<Claims> claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);

        return claims.getPayload().getSubject();
    }

    public boolean isTokenValid(String token, String username) {
        try {
            return username.equals(extractUsername(token));
        } catch (Exception e) {
            return false;
        }
    }
}