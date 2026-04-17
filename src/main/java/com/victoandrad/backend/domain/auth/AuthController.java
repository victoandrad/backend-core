package com.victoandrad.backend.domain.auth;

import com.victoandrad.backend.domain.auth.dto.request.LoginRequest;
import com.victoandrad.backend.domain.auth.dto.request.RegisterRequest;
import com.victoandrad.backend.domain.auth.dto.response.LoginResponse;
import com.victoandrad.backend.domain.auth.dto.response.RegisterResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // ==============================
    // FIELDS
    // ==============================

    private final AuthService authService;

    // ==============================
    // CONSTRUCTORS
    // ==============================

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ==============================
    // METHODS
    // ==============================

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    // ==============================

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request,
            UriComponentsBuilder uriComponentsBuilder
    ) {

        RegisterResponse response = authService.register(request);

        var uri = uriComponentsBuilder
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(response);
    }
}
