package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.ConsultSpecificExecutionStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.InternalErrorException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ConsultSpecificStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.SpecificExecutionStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.consult.ConsultSpecificExecutionStepCandidacyStrategy;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.INTG_002;

@Service
public final class ConsultSpecificExecutionStepCandidacyUseCase {
    private final List<ConsultSpecificExecutionStepCandidacyStrategy> strategies;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ConsultSpecificExecutionStepCandidacyUseCase(
            final List<ConsultSpecificExecutionStepCandidacyStrategy> strategies,
            final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.strategies = strategies;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public SpecificExecutionStepCandidacyDto execute(final Candidate candidate, final ConsultSpecificStepCandidacyDto dto) {
        final ConsultSpecificExecutionStepCandidacyStrategy strategy = this.strategies.stream()
                .filter(it -> it.getTypeStep() == dto.getType())
                .findFirst()
                .orElseThrow(() -> new InternalErrorException(INTG_002));

        final SpecificExecutionStepCandidacyDto step = candidate.findSpecificStepCandidacy(strategy, dto);

        this.applicationEventPublisher.publishEvent(
                new ConsultSpecificExecutionStepCandidacy(
                        step.getStepIdentifier(),
                        step.getCandidacyIdentifier(),
                        step.getCandidateIdentifier(),
                        step.getSelectiveProcessIdentifier(),
                        dto.getType().name()
                )
        );
        return step;
    }

}
