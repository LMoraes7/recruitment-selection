package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.Functionality;

import java.util.Objects;

public final class Function {
    private String identifier;
    private Functionality name;

    public Function(final String identifier) {
        this.identifier = identifier;
    }

    public Function(final String identifier, final Functionality name) {
        this.identifier = identifier;
        this.name = name;
    }

    public Function(final Functionality name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Functionality getName() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Function function = (Function) object;
        return Objects.equals(identifier, function.identifier) && name == function.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, name);
    }

}
