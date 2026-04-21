package com.victoandrad.backend.infra.email;

public interface EmailSender {

    EmailResult send(EmailMessage emailMessage);
}
