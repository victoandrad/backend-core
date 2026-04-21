package com.victoandrad.backend.infra.email;

public record EmailResult(
        boolean success,
        String error
) {
    public static EmailResult ok() {
        return new EmailResult(true, null);
    }

    public static EmailResult fail(String error) {
        return new EmailResult(false, error);
    }
}
