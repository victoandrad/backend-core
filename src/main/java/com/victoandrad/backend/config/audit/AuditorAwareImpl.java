package com.victoandrad.backend.config.audit;

import com.victoandrad.backend.config.security.userdetails.CustomUserDetails;
import com.victoandrad.backend.domain.user.User;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component("auditorAwareImpl")
public class AuditorAwareImpl implements AuditorAware<User> {

    @Override
    @NonNull
    public Optional<User> getCurrentAuditor() {

        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null ||
                auth instanceof org.springframework.security.authentication.AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        if (auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            return Optional.of(userDetails.getUser());
        }

        return Optional.empty();
    }
}