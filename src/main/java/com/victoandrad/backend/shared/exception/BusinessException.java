package com.victoandrad.backend.shared.exception;

public class BusinessException extends RuntimeException {

    // ==============================
    // CONSTRUCTORS
    // ==============================

    public BusinessException(String message) {
        super(message);
    }
}
