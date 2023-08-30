package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.profile.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile.dto.ProfileDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.profile.request.ProfileRequest;

public final class ConverterHelper {

    public static ProfileDto toDto(final ProfileRequest request) {
        return new ProfileDto(request.getName(), request.getFunctionsIdentifiers());
    }

}
