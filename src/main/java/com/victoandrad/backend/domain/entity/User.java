package com.victoandrad.backend.domain.entity;

import com.victoandrad.backend.domain.enums.Gender;
import com.victoandrad.backend.domain.valueobject.Email;
import com.victoandrad.backend.domain.valueobject.Phone;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "email", nullable = false, unique = true))
    private Email email;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "phone", nullable = false))
    private Phone phone;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private final Set<Role> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_permissions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private final Set<Permission> directPermissions = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_denied_permissions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "denied_permission_id")
    )
    private final Set<Permission> deniedPermissions = new HashSet<>();

    protected User() {
    }

    public User(
            String firstName,
            String lastName,
            String username,
            Email email,
            Phone phone,
            String password
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public Set<Permission> getAllowedPermissions() {
        Set<Permission> allowedPermissions = new HashSet<>();
        roles.forEach(
                role -> allowedPermissions.addAll(role.getPermissions())
        );
        allowedPermissions.addAll(directPermissions);
        allowedPermissions.removeAll(deniedPermissions);
        return allowedPermissions;
    }

    public boolean hasRole(Role role) {
        return roles.contains(role);
    }

    public boolean hasRole(String roleName) {
        return roles.stream().anyMatch(r -> r.getName().equalsIgnoreCase(roleName));
    }

    public boolean hasPermission(Permission permission) {
        return getAllowedPermissions().contains(permission);
    }

    public boolean hasPermission(String permissionName) {
        return getAllowedPermissions().stream().anyMatch(p -> p.getName().equalsIgnoreCase(permissionName));
    }
}
