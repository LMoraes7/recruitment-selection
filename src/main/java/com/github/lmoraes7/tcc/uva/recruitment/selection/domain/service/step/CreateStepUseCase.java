package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.NewRegisteredStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.InternalErrorException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Step;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.StepDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.create.CreateStepStrategy;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.INTG_002;

@Service
public final class CreateStepUseCase {
    private final Collection<CreateStepStrategy> createStepStrategies;
    private final ApplicationEventPublisher applicationEventPublisher;

    public CreateStepUseCase(
            final Collection<CreateStepStrategy> createStepStrategies,
            final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.createStepStrategies = createStepStrategies;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public Step execute(final Employee employee, final StepDto dto) {
        final CreateStepStrategy strategy = this.createStepStrategies.stream()
                .filter(it -> it.getTypeStep() == dto.getType())
                .findFirst()
                .orElseThrow(() -> new InternalErrorException(INTG_002));

        final Step step = employee.createStep(dto, strategy);

        this.applicationEventPublisher.publishEvent(
                new NewRegisteredStep(
                    step.getData().getIdentifier(),
                    step.getData().getType()
                )
        );

        return step;
    }

}
