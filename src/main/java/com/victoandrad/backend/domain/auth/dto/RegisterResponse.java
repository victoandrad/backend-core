package com.victoandrad.backend.domain.auth.dto;

public record RegisterResponse(
        Long id,
        String username,
        String email
) {}
