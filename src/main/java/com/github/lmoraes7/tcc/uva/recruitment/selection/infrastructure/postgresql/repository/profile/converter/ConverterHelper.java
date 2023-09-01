package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.entity.ProfileEntity;

public final class ConverterHelper {

    public static ProfileEntity toEntity(final Profile profile) {
        return new ProfileEntity(
                profile.getIdentifier(),
                profile.getName()
        );
    }

}
