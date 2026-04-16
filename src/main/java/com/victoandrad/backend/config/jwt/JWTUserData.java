package com.victoandrad.backend.config.jwt;

import lombok.Builder;

@Builder
public record JWTUserData(
        Long userId,
        String email
) {}
