package com.victoandrad.backend.config.security;

import lombok.Builder;

@Builder
public record JWTUserData(
        Long userId,
        String email
) {}
