package com.victoandrad.backend.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.victoandrad.backend.domain.entity.User;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

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
                .withSubject(user.getEmail().getValue())
                .withExpiresAt(Instant.now().plusSeconds(expirationTime))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }

    public Optional<JWTUserData> validateToken(String token) {
        if (token.isBlank()) {
            return Optional.empty();
        }
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(token);

            Long userId = decodedJWT.getClaim("userId").asLong();
            String email = decodedJWT.getSubject();

            if (userId == null || email.isEmpty()) {
                return Optional.empty();
            }

            return Optional.of(new JWTUserData(userId, email));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
