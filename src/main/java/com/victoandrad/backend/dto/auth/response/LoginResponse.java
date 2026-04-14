package com.victoandrad.backend.dto.auth.response;

public record LoginResponse(
        String accessToken,
        String tokenType,
        Long expiresIn
) {}
