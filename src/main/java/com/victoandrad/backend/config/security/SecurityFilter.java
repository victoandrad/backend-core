package com.victoandrad.backend.config.security;

import com.victoandrad.backend.config.JWTUserData;
import com.victoandrad.backend.config.TokenConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    // ==============================
    // FIELDS
    // ==============================

    private final UserDetailsService userDetailsService;

    private final TokenConfig tokenConfig;

    // ==============================
    // CONSTRUCTORS
    // ==============================

    public SecurityFilter(
            UserDetailsService userDetailsService,
            TokenConfig tokenConfig
    ) {
        this.userDetailsService = userDetailsService;
        this.tokenConfig = tokenConfig;
    }

    // ==============================
    // METHODS
    // ==============================

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {

            String header = request.getHeader("Authorization");

            if (Strings.isNotEmpty(header)
                    && header.startsWith("Bearer ")
                    && SecurityContextHolder.getContext().getAuthentication() == null) {

                String token = header.substring(7);

                Optional<JWTUserData> optUser = tokenConfig.validateToken(token);

                if (optUser.isPresent()) {

                    JWTUserData userData = optUser.get();

                    UserDetails userDetails;

                    try {
                        userDetails = userDetailsService.loadUserByUsername(userData.email());
                    } catch (UsernameNotFoundException ex) {
                        // usuário não existe mais → token inválido
                        SecurityContextHolder.clearContext();
                        filterChain.doFilter(request, response);
                        return;
                    }

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }

            filterChain.doFilter(request, response);

        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
        }
    }
}
