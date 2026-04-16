package com.victoandrad.backend.infra.debug;

import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@Profile("dev")
public class TestController {

    // ==============================
    // METHODS
    // ==============================

    @PreAuthorize( "hasAuthority('ADMIN_USER')")
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}
