package com.victoandrad.backend.config.security.userdetails;

import com.victoandrad.backend.domain.valueobject.Email;
import com.victoandrad.backend.repository.UserRepository;
import org.jspecify.annotations.NonNull;
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

    private final UserRepository userRepository;

    // ==============================
    // CONSTRUCTORS
    // ==============================

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ==============================
    // METHODS
    // ==============================

    @Override
    @NonNull
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(new Email(email))
                .map(CustomUserDetails::new)
                .orElseThrow(
                        () -> new UsernameNotFoundException(email)
                );
    }
}
