package com.victoandrad.backend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    // ==============================
    // FIELDS
    // ==============================

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(name = "name", nullable = false, unique = true, updatable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private final Set<Permission> permissions = new HashSet<>();

    // ==============================
    // CONSTRUCTORS
    // ==============================

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

    // ==============================
    // METHODS
    // ==============================

    public void addPermission(Permission permission) {
        permissions.add(permission);
    }

    public void removePermission(Permission permission) {
        permissions.remove(permission);
    }

    public boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }

    public Set<Permission> getPermissions() {
        return Set.copyOf(permissions);
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions.clear();
        this.permissions.addAll(permissions);
    }
}
