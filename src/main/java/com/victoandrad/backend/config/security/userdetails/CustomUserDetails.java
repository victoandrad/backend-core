package com.victoandrad.backend.config.security.userdetails;

import com.victoandrad.backend.domain.entity.User;
import lombok.Getter;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    // ==============================
    // FIELDS
    // ==============================

    @Getter
    private final User user;

    // ==============================
    // CONSTRUCTORS
    // ==============================

    public CustomUserDetails(User user) {
        this.user = user;
    }

    // ==============================
    // METHODS
    // ==============================

    @Override
    @NonNull
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAllowedPermissions()
                .stream()
                .map(permission -> (GrantedAuthority) permission::getName)
                .toList();
    }

    // ==============================

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    // ==============================

    @Override
    @NonNull
    public String getUsername() {
        return user.getUsername();
    }

    // ==============================

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    // ==============================

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    // ==============================

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    // ==============================

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
