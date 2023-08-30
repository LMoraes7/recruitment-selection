package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Function;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile.dto.ProfileDto;

import java.util.stream.Collectors;

public final class ConverterHelper {

    public static Profile toModel(final ProfileDto dto) {
        return new Profile(
                GeneratorIdentifier.forProfile(),
                dto.getName(),
                dto.getFunctionsIdentifiers().stream().map(Function::new).collect(Collectors.toSet())
        );
    }

}
