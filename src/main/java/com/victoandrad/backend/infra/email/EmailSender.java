package com.victoandrad.backend.infra.email;

import java.util.concurrent.CompletableFuture;

public interface EmailSender {

    CompletableFuture<EmailResult> send(EmailMessage emailMessage);
}
