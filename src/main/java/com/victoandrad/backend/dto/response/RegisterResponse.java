package com.victoandrad.backend.dto.response;

public record RegisterResponse(
        Long id,
        String username,
        String email
) {}
