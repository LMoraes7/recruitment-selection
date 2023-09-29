package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.execute;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ExecuteStepCandidacyDto;

public interface ExecuteStepCandidacyStrategy {
    TypeStep getTypeStep();

    void execute(final String candidateIdentifier, final ExecuteStepCandidacyDto dto);
}
