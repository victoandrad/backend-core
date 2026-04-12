package com.victoandrad.backend.controller.v1;

import com.victoandrad.backend.domain.entity.User;
import com.victoandrad.backend.dto.request.LoginRequest;
import com.victoandrad.backend.dto.request.RegisterRequest;
import com.victoandrad.backend.dto.response.LoginResponse;
import com.victoandrad.backend.dto.response.RegisterResponse;
import com.victoandrad.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request,
            UriComponentsBuilder uriComponentsBuilder
    ) {

        User user = authService.register(request);

        var uri = uriComponentsBuilder
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(new RegisterResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail().getValue())
                );
    }
}
