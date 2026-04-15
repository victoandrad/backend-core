package com.victoandrad.backend.domain.permission;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "permissions")
@Getter
public class Permission {

    // ==============================
    // FIELDS
    // ==============================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, updatable = false)
    private String name;

    // ==============================
    // CONSTRUCTORS
    // ==============================

    protected Permission() {
    }

    public Permission(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Invalid permission name");
        }
        String normalized = name.toUpperCase().trim();
        if (!normalized.matches("^[A-Z]+(_[A-Z]+)*$")) {
            throw new IllegalArgumentException("Invalid permission format");
        }
        this.name = normalized;
    }
}
