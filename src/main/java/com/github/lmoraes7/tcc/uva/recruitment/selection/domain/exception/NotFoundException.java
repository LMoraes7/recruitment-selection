package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error;

import java.util.Collection;
import java.util.Collections;

public final class NotFoundException extends RuntimeException {
    private final String code;
    private final Class<?> classType;

    public NotFoundException(final String code, final Class<?> classType) {
        this.code = code;
        this.classType = classType;
    }

    public String getCode() {
        return code;
    }

    public Class<?> getClassType() {
        return classType;
    }

}
