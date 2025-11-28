package com.comy_delivery_back.service;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.comy_delivery_back.model.Usuario;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    @Value("${JWT_SECRETKEY}")
    private String jwtSecretKey;

    @Value("${JWT_EXPIRATIONTIME}")
    private long expirationMinutes; //curta

    private final String ISSUER = "comy-delivery-api"; //quem assina o token

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Usuario usuario){
        long expirationTimeMillis = TimeUnit.MINUTES.toMillis(expirationMinutes);
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTimeMillis);

        String role = usuario.getRoleUsuario().name();

        try{
            return Jwts.builder()
                    .issuer(ISSUER)
                    .subject(usuario.getUsername())
                    .claim("userId", usuario.getId())
                    .claim("roles", role)
                    .issuedAt(new Date())
                    .expiration(expirationDate)
                    .signWith(getSecretKey())
                    .compact();
        }catch (JWTCreationException e){
            throw new RuntimeException("Erro ao gerar token: ", e);
        }

    }

    public String refreshToken(Usuario usuario){

        long expirationTimeMillis = TimeUnit.DAYS.toMillis(1); //1 dia
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTimeMillis);
        String role = usuario.getRoleUsuario().name();

        try{
            return Jwts.builder()
                    .issuer(ISSUER)
                    .subject(usuario.getUsername())
                    .claim("role", role)
                    .claim("userId", usuario.getId())
                    .expiration(expirationDate) //24 horas
                    .signWith(getSecretKey())
                    .compact();

        }catch (JWTCreationException e){
            throw new RuntimeException("Erro ao gerar refresh token: ", e);
        }
    }

    public String validateRefreshToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject(); // Retorna o username (subject) do token

        } catch (JwtException e) {
            throw new RuntimeException("Refresh token inválido ou expirado. Faça login novamente.", e);
        }
    }

}
