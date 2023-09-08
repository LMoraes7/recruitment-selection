package com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeQuestion;

import java.util.Objects;

public final class NewRegisteredQuestion {
    private final String identifier;
    private final TypeQuestion type;

    public NewRegisteredQuestion(final String identifier, final TypeQuestion type) {
        this.identifier = identifier;
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public TypeQuestion getType() {
        return type;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        NewRegisteredQuestion that = (NewRegisteredQuestion) object;
        return Objects.equals(identifier, that.identifier) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, type);
    }

}
