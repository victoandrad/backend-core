package com.victoandrad.backend.domain.auth.dto.response;

public record RegisterResponse(
        Long id,
        String username,
        String email
) {}
