package com.victoandrad.backend.domain.user.enums;

import lombok.Getter;

public enum Gender {

    // ==============================
    // FIELDS
    // ==============================

    MALE(1, "Male"),
    FEMALE(2, "Female"),
    NON_BINARY(3, "Non-binary"),
    OTHER(4, "Other"),
    PREFER_NOT_TO_SAY(5, "Prefer not to say");

    @Getter
    private final int id;

    @Getter
    private final String label;

    // ==============================
    // CONSTRUCTORS
    // ==============================

    Gender(int id, String name) {
        this.id = id;
        this.label = name;
    }

    // ==============================
    // METHODS
    // ==============================

    public static Gender fromId(int id) {
        for (Gender gender : Gender.values()) {
            if (gender.getId() == id) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Invalid gender id: " + id);
    }
}