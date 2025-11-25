package com.comy_delivery_back.service;

import com.comy_delivery_back.model.Usuario;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    @Value("${JWT_SECRETKEY}")
    private String jwtSecretKey;

    @Value("${JWT_EXPIRATIONTIME}")
    private long expirationMinutes;


    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Usuario usuario){
        long expirationTimeMillis = TimeUnit.MINUTES.toMillis(expirationMinutes);
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTimeMillis);

        return Jwts.builder()
                .subject(usuario.getUsername())
                .claim("userId", usuario.getId())
                .issuedAt(new Date())
                .expiration(expirationDate)
                .signWith(getSecretKey())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject(); // Retorna o username (subject) do token

        } catch (JwtException e) {
            return null;
        }
    }

}
