package com.victoandrad.backend.domain.user;

import com.victoandrad.backend.domain.permission.Permission;
import com.victoandrad.backend.domain.role.Role;
import com.victoandrad.backend.domain.user.enums.Gender;
import com.victoandrad.backend.domain.common.valueobject.Email;
import com.victoandrad.backend.domain.common.valueobject.Phone;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    // ==============================
    // FIELDS
    // ==============================

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Getter
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Getter
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Getter
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "email", nullable = false, unique = true))
    private Email email;

    @Getter
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "phone", nullable = false))
    private Phone phone;

    @Getter
    @Column(name = "password", nullable = false)
    private String password;

    @Getter
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Getter
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

    // ==============================
    // CONSTRUCTORS
    // ==============================

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

    // ==============================
    // METHODS
    // ==============================

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

    public boolean hasPermission(Permission permission) {
        return getAllowedPermissions().contains(permission);
    }

    public Set<Role> getRoles() {
        return Set.copyOf(roles);
    }

    public Set<Permission> getDirectPermissions() {
        return Set.copyOf(directPermissions);
    }

    public Set<Permission> getDeniedPermissions() {
        return Set.copyOf(deniedPermissions);
    }
}
