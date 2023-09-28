package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.consult;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.StepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ConsultSpecificStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.SpecificExecutionStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.TheoricalTestStepCandidacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class ConsultTheoricalTestExecutionStepCandidacy implements ConsultSpecificExecutionStepCandidacyStrategy {
    private final TheoricalTestStepCandidacyRepository theoricalTestStepCandidacyRepository;

    @Autowired
    public ConsultTheoricalTestExecutionStepCandidacy(final TheoricalTestStepCandidacyRepository theoricalTestStepCandidacyRepository) {
        this.theoricalTestStepCandidacyRepository = theoricalTestStepCandidacyRepository;
    }

    @Override
    public TypeStep getTypeStep() {
        return TypeStep.THEORETICAL_TEST;
    }

    @Override
    public SpecificExecutionStepCandidacyDto execute(final String candidateIdentifier, final ConsultSpecificStepCandidacyDto dto) {
        return this.theoricalTestStepCandidacyRepository.findQuestions(
                dto.getCandidacyIdentifier(),
                candidateIdentifier,
                dto.getSelectiveProcessIdentifier(),
                dto.getStepIdentifier()
        ).orElseThrow(() -> new NotFoundException(dto.getStepIdentifier(), StepCandidacy.class));
    }

}
