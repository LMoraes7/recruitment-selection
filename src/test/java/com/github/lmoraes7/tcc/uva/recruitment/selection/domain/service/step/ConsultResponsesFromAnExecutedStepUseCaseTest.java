package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.ConsultExecutedStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ConsultResponsesFromAnExecutedStepDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ResponsesFromAnExecutedStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ResponsesFromAnExecutedUploadFileStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.consult.answer.ConsultResponsesFromAnExecutedStepStrategy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.FIND_FILES_UPLOADS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.only;

final class ConsultResponsesFromAnExecutedStepUseCaseTest {
    private final ConsultResponsesFromAnExecutedStepStrategy strategy = mock(ConsultResponsesFromAnExecutedStepStrategy.class);
    private final List<ConsultResponsesFromAnExecutedStepStrategy> strategies = List.of(strategy);
    private final ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
    private final ConsultResponsesFromAnExecutedStepUseCase consultResponsesFromAnExecutedStepUseCase = new ConsultResponsesFromAnExecutedStepUseCase(this.strategies, this.applicationEventPublisher);

    private Employee employee;
    private ConsultResponsesFromAnExecutedStepDto consultResponsesFromAnExecutedStepDto;
    private ResponsesFromAnExecutedStep responsesFromAnExecutedStep;

    @BeforeEach
    void setUp() {
        this.employee = TestUtils.dummyObject(Employee.class);
        this.consultResponsesFromAnExecutedStepDto = TestUtils.dummyObject(ConsultResponsesFromAnExecutedStepDto.class);
        this.responsesFromAnExecutedStep = TestUtils.dummyObject(ResponsesFromAnExecutedStep.class);
    }

    @Test
    void when_prompted_it_should_query_the_files_successfully() {
        when(this.strategy.getType()).thenReturn(this.consultResponsesFromAnExecutedStepDto.getTypeStep());
        when(this.strategy.execute(this.consultResponsesFromAnExecutedStepDto)).thenReturn(this.responsesFromAnExecutedStep);

        assertDoesNotThrow(() -> this.consultResponsesFromAnExecutedStepUseCase.execute(this.employee, this.consultResponsesFromAnExecutedStepDto));

        verify(this.strategy, times(1)).getType();
        verify(this.strategy, times(1)).execute(this.consultResponsesFromAnExecutedStepDto);
        verify(this.applicationEventPublisher, only()).publishEvent(
                new ConsultExecutedStepCandidacy(
                        this.consultResponsesFromAnExecutedStepDto.getStepIdentifier(),
                        this.consultResponsesFromAnExecutedStepDto.getApplicationIdentifier(),
                        this.consultResponsesFromAnExecutedStepDto.getTypeStep().name()
                )
        );
        verifyNoMoreInteractions(this.strategy);
    }

}