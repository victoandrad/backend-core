package com.victoandrad.backend.infra.email;

public record EmailMessage(
        String to,
        String subject,
        String body
) {}
