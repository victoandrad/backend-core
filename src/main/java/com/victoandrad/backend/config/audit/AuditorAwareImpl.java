package com.victoandrad.backend.config.audit;

import com.victoandrad.backend.config.security.userdetails.CustomUserDetails;
import com.victoandrad.backend.domain.user.User;
import com.victoandrad.backend.domain.user.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component("auditorAwareImpl")
public class AuditorAwareImpl implements AuditorAware<User> {

    // ==============================
    // FIELDS
    // ==============================

    private final UserRepository userRepository;

    // ==============================
    // CONSTRUCTORS
    // ==============================

    @Autowired
    public AuditorAwareImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ==============================
    // METHODS
    // ==============================

    @Override
    @NonNull
    public Optional<User> getCurrentAuditor() {

        var auth = SecurityContextHolder.getContext().getAuthentication();

        return Optional.ofNullable(auth)
                .filter(a -> a.isAuthenticated()
                        && !(a instanceof org.springframework.security.authentication.AnonymousAuthenticationToken))
                .map(a -> {
                    Object principal = a.getPrincipal();

                    if (principal instanceof CustomUserDetails userDetails) {
                        return userDetails.getUser();
                    }

                    return null;
                })
                .or(() -> userRepository.findById(1L)); // SYSTEM USER fallback
    }
}