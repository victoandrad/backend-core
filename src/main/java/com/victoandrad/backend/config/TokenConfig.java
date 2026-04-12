package com.victoandrad.backend.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.victoandrad.backend.domain.entity.User;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TokenConfig {

    // ==============================
    // FIELDS
    // ==============================

    @Value("${jwt.secret}")
    private String secret;

    @Getter
    @Value("${jwt.expiration-time}")
    private Long expirationTime;

    // ==============================
    // METHODS
    // ==============================

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withClaim("userId", user.getId())
                .withSubject(user.getUsername())
                .withExpiresAt(Instant.now().plusSeconds(expirationTime))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }
}
