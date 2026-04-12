package com.victoandrad.backend.config.security.userdetails;

import com.victoandrad.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // ==============================
    // METHODS
    // ==============================

    private final AuthService authService;

    // ==============================
    // CONSTRUCTORS
    // ==============================

    @Autowired
    public CustomUserDetailsService(AuthService authService) {
        this.authService = authService;
    }

    // ==============================
    // METHODS
    // ==============================

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authService.loadUserByUsername(username);
    }
}
