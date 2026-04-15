package com.victoandrad.backend.domain.auth.dto;

public record LoginResponse(
        String accessToken,
        String tokenType
) {}
