package com.victoandrad.backend.domain.auth.dto.response;

public record LoginResponse(
        String accessToken,
        String tokenType
) {}
