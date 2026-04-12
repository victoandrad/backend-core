package com.victoandrad.backend.domain.valueobject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
public class Email {

    // ==============================
    // FIELDS
    // ==============================

    @Getter
    private String value;

    // ==============================
    // CONSTRUCTORS
    // ==============================

    protected Email() {
    }

    public Email(String value) {
        if (value == null || !value.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email");
        }
        this.value = value.toLowerCase();
    }
}
