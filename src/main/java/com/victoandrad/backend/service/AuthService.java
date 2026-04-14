package com.victoandrad.backend.service;

import com.victoandrad.backend.config.TokenConfig;
import com.victoandrad.backend.config.security.userdetails.CustomUserDetails;
import com.victoandrad.backend.domain.entity.User;
import com.victoandrad.backend.domain.valueobject.Email;
import com.victoandrad.backend.domain.valueobject.Phone;
import com.victoandrad.backend.dto.auth.request.LoginRequest;
import com.victoandrad.backend.dto.auth.request.RegisterRequest;
import com.victoandrad.backend.dto.auth.response.LoginResponse;
import com.victoandrad.backend.dto.auth.response.RegisterResponse;
import com.victoandrad.backend.repository.UserRepository;
import com.victoandrad.backend.service.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    // ==============================
    // FIELDS
    // ==============================

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final TokenConfig tokenConfig;

    // ==============================
    // CONSTRUCTORS
    // ==============================

    @Autowired
    public AuthService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            TokenConfig tokenConfig
    ) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenConfig = tokenConfig;
    }

    // ==============================
    // METHODS
    // ==============================

    public LoginResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        );

        Authentication authentication = authenticationManager.authenticate(userAndPass);
        Object principal = authentication.getPrincipal();

        if (!(principal instanceof CustomUserDetails userDetails)) {
            throw new RuntimeException("Invalid authentication principal");
        }

        if (userDetails.getUser() == null) {
            throw new BusinessException("User not found in UserDetails");
        }

        String token = tokenConfig.generateToken(userDetails.getUser());
        return new LoginResponse(
                token,
                "Bearer",
                tokenConfig.getExpirationTime()
        );
    }

    // ==============================

    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(new Email(request.email()))) {
            throw new BusinessException("Email already in use");
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new BusinessException("Username already in use");
        }

        User newUser = new User(
                request.firstName(),
                request.lastName(),
                request.username(),
                new Email(request.email()),
                new Phone(request.phone()),
                passwordEncoder.encode(request.password())
        );

        User user = userRepository.save(newUser);
        return new RegisterResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail().getValue()
        );
    }
}
