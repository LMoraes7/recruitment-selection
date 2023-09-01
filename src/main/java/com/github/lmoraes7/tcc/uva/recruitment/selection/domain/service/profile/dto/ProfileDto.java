package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile.dto;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public final class ProfileDto {
    private final String name;
    private final Set<String> functionsIdentifiers;

    public ProfileDto(final String name, final Set<String> functionsIdentifiers) {
        this.name = name;
        this.functionsIdentifiers = functionsIdentifiers;
    }

    public String getName() {
        return name;
    }

    public Set<String> getFunctionsIdentifiers() {
        return Collections.unmodifiableSet(functionsIdentifiers);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ProfileDto that = (ProfileDto) object;
        return Objects.equals(name, that.name) && Objects.equals(functionsIdentifiers, that.functionsIdentifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, functionsIdentifiers);
    }

}
