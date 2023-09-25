package com.gnsoftware.Ordem.Servico.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.expiration}")
    private Long tempoexpiracaoToken;

    @Value("${jwt.secret}")
    private String chaveSecretaToken;

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + tempoexpiracaoToken))
                .signWith(SignatureAlgorithm.HS512, chaveSecretaToken.getBytes())
                .compact();
    }
}
