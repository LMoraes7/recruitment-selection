package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.consult;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ConsultSpecificStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.SpecificExecutionStepCandidacyDto;

public interface ConsultSpecificExecutionStepCandidacyStrategy {
    TypeStep getTypeStep();

    SpecificExecutionStepCandidacyDto execute(final String candidateIdentifier, final ConsultSpecificStepCandidacyDto dto);
}
