package com.victoandrad.backend.dto.auth.response;

public record RegisterResponse(
        Long id,
        String username,
        String email
) {}
