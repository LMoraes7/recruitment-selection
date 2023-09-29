package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.consult;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.StepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ConsultSpecificStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.SpecificExecutionStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.ExternalStepCandidacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class ConsultExternalExecutionStepCandidacy implements ConsultSpecificExecutionStepCandidacyStrategy {
    private final ExternalStepCandidacyRepository externalStepCandidacyRepository;

    @Autowired
    public ConsultExternalExecutionStepCandidacy(final ExternalStepCandidacyRepository externalStepCandidacyRepository) {
        this.externalStepCandidacyRepository = externalStepCandidacyRepository;
    }

    @Override
    public TypeStep getTypeStep() {
        return TypeStep.EXTERNAL;
    }

    @Override
    public SpecificExecutionStepCandidacyDto execute(final String candidateIdentifier, final ConsultSpecificStepCandidacyDto dto) {
        return this.externalStepCandidacyRepository.find(
                dto.getCandidacyIdentifier(),
                candidateIdentifier,
                dto.getSelectiveProcessIdentifier(),
                dto.getStepIdentifier()
        ).orElseThrow(() -> new NotFoundException(dto.getStepIdentifier(), StepCandidacy.class));
    }

}
