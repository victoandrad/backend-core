package com.victoandrad.backend.dto.response;

public record LoginResponse(
        String accessToken,
        String tokenType,
        Long expiresIn
) {}
