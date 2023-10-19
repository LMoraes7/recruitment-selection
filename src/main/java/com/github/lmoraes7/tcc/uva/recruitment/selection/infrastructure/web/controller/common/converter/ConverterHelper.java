package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.common.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.dto.RedefinePasswordDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.dto.RequestPasswordResetDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.common.request.RedefinePasswordRequest;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.common.request.ResetPasswordRequest;

public final class ConverterHelper {

    public static RedefinePasswordDto toDto(final RedefinePasswordRequest request) {
        return new RedefinePasswordDto(
                request.getCode(),
                request.getNewPassword()
        );
    }

    public static RequestPasswordResetDto toDto(final ResetPasswordRequest request) {
        return new RequestPasswordResetDto(
                request.getEmail()
        );
    }

}
