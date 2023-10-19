package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception;

public final class LoginException extends RuntimeException {
    private final String message;

    public LoginException(final String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}