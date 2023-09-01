package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public final class Profile {
    private String identifier;
    private String name;
    private Set<Function> functions;

    public Profile(final String identifier) {
        this.identifier = identifier;
    }

    public Profile(final String identifier, final String name) {
        this.identifier = identifier;
        this.name = name;
    }

    public Profile(final String identifier, final String name, final Set<Function> functions) {
        this.identifier = identifier;
        this.name = name;
        this.functions = functions;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public Set<Function> getFunctions() {
        return Collections.unmodifiableSet(functions);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Profile profile = (Profile) object;
        return Objects.equals(identifier, profile.identifier) && Objects.equals(name, profile.name) && Objects.equals(functions, profile.functions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, name, functions);
    }

}
