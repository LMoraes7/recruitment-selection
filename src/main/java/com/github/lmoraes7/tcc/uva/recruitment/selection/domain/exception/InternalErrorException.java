package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error;

public final class InternalErrorException extends RuntimeException {
    private final Error error;

    public InternalErrorException(final Error error) {
        this.error = error;
    }

    public Error getError() {
        return error;
    }

}
