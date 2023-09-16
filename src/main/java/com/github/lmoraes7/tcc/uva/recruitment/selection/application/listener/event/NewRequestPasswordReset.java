package com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeEntity;

import java.util.Objects;

public final class NewRequestPasswordReset {
    private final String email;
    private final TypeEntity typeEntity;

    public NewRequestPasswordReset(final String email, final TypeEntity typeEntity) {
        this.email = email;
        this.typeEntity = typeEntity;
    }

    public String getEmail() {
        return email;
    }

    public TypeEntity getTypeEntity() {
        return typeEntity;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        NewRequestPasswordReset that = (NewRequestPasswordReset) object;
        return Objects.equals(email, that.email) && typeEntity == that.typeEntity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, typeEntity);
    }

}
