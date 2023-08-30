package com.github.lmoraes7.tcc.uva.recruitment.selection.utils;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Function;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import org.mockito.ArgumentMatcher;

import java.util.stream.Collectors;

public final class ProfileArgMatchers implements ArgumentMatcher<Profile> {

    private final Profile profile;

    public ProfileArgMatchers(final Profile profile) {
        this.profile = profile;
    }

    @Override
    public boolean matches(final Profile p0) {
        if (p0 == null)
            return false;

        return this.profile.getName().equals(p0.getName()) &&
                this.profile.getFunctions().stream().map(Function::getIdentifier).collect(Collectors.toSet())
                        .equals(p0.getFunctions().stream().map(Function::getIdentifier).collect(Collectors.toSet()));
    }

}
