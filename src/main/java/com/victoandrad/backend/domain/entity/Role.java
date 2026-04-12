package com.victoandrad.backend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, updatable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private final Set<Permission> permissions = new HashSet<>();

    protected Role() {
    }

    public Role(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Invalid role name");
        }
        String normalized = name.toUpperCase().trim();
        if (!normalized.matches("^[A-Z]+(_[A-Z]+)*$")) {
            throw new IllegalArgumentException("Invalid role format");
        }
        this.name = normalized;
    }
}
