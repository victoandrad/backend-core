package com.victoandrad.backend.infra.debug;

import com.victoandrad.backend.infra.email.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email-test")
public class EmailTestController {

    // ==============================
    // FIELDS
    // ==============================

    private final EmailSender emailSender;

    // ==============================
    // CONSTRUCTORS
    // ==============================

    @Autowired
    public EmailTestController(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    // ==============================
    // METHODS
    // ==============================

    @PostMapping
    public ResponseEntity<String> sendEmail() {
        emailSender.send(
                "victoandrad@edu.unifil.br",
                "Testing",
                "Working"
        );
        return ResponseEntity.ok("String");
    }
}
