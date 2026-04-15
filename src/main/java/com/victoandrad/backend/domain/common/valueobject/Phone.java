package com.victoandrad.backend.domain.common.valueobject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
public class Phone {

    // ==============================
    // FIELDS
    // ==============================

    @Getter
    private String value;

    // ==============================
    // CONSTRUCTORS
    // ==============================

    protected Phone() {
    }

    public Phone(String value) {
        if (value == null || value.length() < 10) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        this.value = value;
    }
}
