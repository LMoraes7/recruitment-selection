package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error;

import java.util.Collection;
import java.util.Collections;

public final class BusinessException extends RuntimeException {
    private final Error error;
    private Collection<?> args;

    public BusinessException(final Error error) {
        this.error = error;
    }

    public BusinessException(final Error error, final Collection<?> args) {
        this.error = error;
        this.args = args;
    }

    public Error getError() {
        return error;
    }

    public Collection<?> getArgs() {
        return Collections.unmodifiableCollection(args);
    }

}
