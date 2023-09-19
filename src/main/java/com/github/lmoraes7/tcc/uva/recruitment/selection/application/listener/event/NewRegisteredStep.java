package com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;

import java.util.Objects;

public final class NewRegisteredStep {
    private final String id;
    private final TypeStep type;

    public NewRegisteredStep(final String id, final TypeStep type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public TypeStep getType() {
        return type;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        NewRegisteredStep that = (NewRegisteredStep) object;
        return Objects.equals(id, that.id) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

}
