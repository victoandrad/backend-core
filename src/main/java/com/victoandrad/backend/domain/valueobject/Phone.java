package com.victoandrad.backend.domain.valueobject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
public class Phone {

    @Getter
    private String value;

    protected Phone() {
    }

    public Phone(String value) {
        if (value == null || value.length() < 10) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        this.value = value;
    }
}
