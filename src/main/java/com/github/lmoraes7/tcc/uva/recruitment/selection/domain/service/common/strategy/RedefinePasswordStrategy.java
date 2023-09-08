package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.strategy;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.PasswordChangeRequest;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.dto.RedefinePasswordDto;

public interface RedefinePasswordStrategy {
    TypeEntity getTypeEntity();

    void execute(final PasswordChangeRequest passwordChangeRequest, final RedefinePasswordDto redefinePasswordDto);
}
