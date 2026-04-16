package com.victoandrad.backend.shared.exception;

public class NotFoundException extends RuntimeException {

    // ==============================
    // CONSTRUCTORS
    // ==============================

    public NotFoundException(String message) {
        super(message);
    }
}
