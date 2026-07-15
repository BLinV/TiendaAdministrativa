package com.store.www.config;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

// Genera y valida tokens JWT.
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    /** Convierte el secreto de texto en la clave criptográfica para firmar. */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /** Crea un token firmado que lleva dentro el username y una fecha de expiración. */
    public String generarToken(String username) {
        Date ahora = new Date();
        Date vence = new Date(ahora.getTime() + expiration);

        return Jwts.builder()
                .subject(username)
                .issuedAt(ahora)
                .expiration(vence)
                .signWith(getSigningKey())  // firma: nadie puede falsificarlo sin el secreto
                .compact();                 // lo serializa al string final
    }

    /** Extrae el username (subject) que viaja dentro del token. */
    public String extraerUsername(String token) {
        return extraerClaim(token, Claims::getSubject);
    }

    /** true si el token es válido para ese username y no ha expirado. */
    public boolean esValido(String token, String username) {
        final String u = extraerUsername(token);
        return u.equals(username) && !expirado(token);
    }

    private boolean expirado(String token) {
        return extraerClaim(token, Claims::getExpiration).before(new Date());
    }

    /** Lee un dato concreto (claim) del token, verificando antes la firma. */
    private <T> T extraerClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())   // si la firma no cuadra, lanza excepción aquí
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return resolver.apply(claims);
    }
}
