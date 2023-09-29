package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.ExecuteStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.InternalErrorException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ExecuteStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.execute.ExecuteStepCandidacyStrategy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.INTG_002;

@Service
public final class ExecuteStepCandidacyUseCase {
    private final List<ExecuteStepCandidacyStrategy> strategies;
    private final StepRepository stepRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public ExecuteStepCandidacyUseCase(
            final List<ExecuteStepCandidacyStrategy> strategies,
            final StepRepository stepRepository,
            final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.strategies = strategies;
        this.stepRepository = stepRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void execute(final Candidate candidate, final ExecuteStepCandidacyDto dto) {
        final ExecuteStepCandidacyStrategy strategy = this.strategies.stream()
                .filter(it -> it.getTypeStep() == dto.getType())
                .findFirst()
                .orElseThrow(() -> new InternalErrorException(INTG_002));

        candidate.executeStep(this.stepRepository, strategy, dto);

        this.applicationEventPublisher.publishEvent(
                new ExecuteStepCandidacy(
                        dto.getStepIdentifier(),
                        dto.getCandidacyIdentifier(),
                        candidate.getIdentifier(),
                        dto.getSelectiveProcessIdentifier(),
                        dto.getType().name()
                )
        );
    }

}
