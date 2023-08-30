package com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event;

import java.util.Objects;

public final class NewRegisteredProfile {
    private final String identifier;
    private final String name;

    public NewRegisteredProfile(
            final String identifier,
            final String name
    ) {
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
        NewRegisteredProfile that = (NewRegisteredProfile) object;
        return Objects.equals(identifier, that.identifier) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, name);
    }

}
