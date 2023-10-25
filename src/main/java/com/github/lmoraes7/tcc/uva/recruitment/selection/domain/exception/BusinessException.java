package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error;

import java.util.Collection;
import java.util.Collections;

public final class BusinessException extends RuntimeException {
    private final Error error;
    private final String message;

    public BusinessException(final Error error, final String message) {
        this.error = error;
        this.message = message;
    }

    public Error getError() {
        return error;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
