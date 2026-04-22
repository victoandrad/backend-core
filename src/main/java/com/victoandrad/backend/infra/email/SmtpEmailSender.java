package com.victoandrad.backend.infra.email;

import com.victoandrad.backend.infra.config.mail.MailProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class SmtpEmailSender implements EmailSender {

    private static final Logger log = LoggerFactory.getLogger(SmtpEmailSender.class);

    private final JavaMailSender mailSender;
    private final MailProperties props;

    @Autowired
    public SmtpEmailSender(JavaMailSender mailSender, MailProperties props) {
        this.mailSender = mailSender;
        this.props = props;
    }

    @Async("mailExecutor")
    @Override
    public CompletableFuture<EmailResult> send(EmailMessage email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(props.getFrom());
            message.setTo(email.to());
            message.setSubject(email.subject());
            message.setText(email.body());

            mailSender.send(message);

            log.info("email_sent to={}", email.to());
            return CompletableFuture.completedFuture(EmailResult.ok());

        } catch (MailException e) {
            log.error("email_smtp_error to={} msg={}", email.to(), e.getMessage(), e);
            return CompletableFuture.completedFuture(EmailResult.fail("SMTP_ERROR"));

        } catch (Exception e) {
            log.error("email_unexpected_error to={}", email.to(), e);
            return CompletableFuture.completedFuture(EmailResult.fail("UNKNOWN_ERROR"));
        }
    }
}