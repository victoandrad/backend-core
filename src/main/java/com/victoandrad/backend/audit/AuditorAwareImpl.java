package com.victoandrad.backend.audit;

import com.victoandrad.backend.config.security.userdetails.CustomUserDetails;
import com.victoandrad.backend.domain.entity.User;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<User> {

    @Override
    @NonNull
    public Optional<User> getCurrentAuditor() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || Objects.equals(auth.getPrincipal(), "anonymousUser")) {
            return Optional.empty();
        }

        var principal = auth.getPrincipal();

        if (principal instanceof CustomUserDetails customUser) {
            return Optional.of(customUser.getUser());
        }

        return Optional.empty();
    }
}
