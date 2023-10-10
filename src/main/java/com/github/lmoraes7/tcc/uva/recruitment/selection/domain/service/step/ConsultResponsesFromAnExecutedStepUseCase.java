package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.ConsultExecutedStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.InternalErrorException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ConsultResponsesFromAnExecutedStepDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ResponsesFromAnExecutedStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.consult.answer.ConsultResponsesFromAnExecutedStepStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.INTG_002;

@Service
public final class ConsultResponsesFromAnExecutedStepUseCase {
    private final List<ConsultResponsesFromAnExecutedStepStrategy> strategies;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public ConsultResponsesFromAnExecutedStepUseCase(
            final List<ConsultResponsesFromAnExecutedStepStrategy> strategies,
            final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.strategies = strategies;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public ResponsesFromAnExecutedStep execute(final Employee employee, final ConsultResponsesFromAnExecutedStepDto dto) {
        final ConsultResponsesFromAnExecutedStepStrategy strategy = this.strategies.stream()
                .filter(it -> it.getType() == dto.getTypeStep())
                .findFirst()
                .orElseThrow(() -> new InternalErrorException(INTG_002));


        final ResponsesFromAnExecutedStep result = employee.consultResponseFromStep(strategy, dto);

        this.applicationEventPublisher.publishEvent(
            new ConsultExecutedStepCandidacy(
                    dto.getStepIdentifier(),
                    dto.getApplicationIdentifier(),
                    dto.getTypeStep().name()
            )
        );
        return result;
    }

}
