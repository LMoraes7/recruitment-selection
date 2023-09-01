package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.entity;

import java.util.Objects;

public final class ProfileEntity {

    private final String identifier;
    private final String name;

    public ProfileEntity(final String identifier, final String name) {
        this.identifier = identifier;
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ProfileEntity that = (ProfileEntity) object;
        return Objects.equals(identifier, that.identifier) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, name);
    }

}
