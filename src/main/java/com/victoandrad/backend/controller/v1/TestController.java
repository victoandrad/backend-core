package com.victoandrad.backend.controller.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    // ==============================
    // METHODS
    // ==============================

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}
