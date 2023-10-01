package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.ExecuteStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.InternalErrorException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ExecuteStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ExecuteStepCandidacyFindDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.execute.ExecuteStepCandidacyStrategy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.StepRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.INTG_002;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class ExecuteStepCandidacyUseCaseTest {
    private final ExecuteStepCandidacyStrategy strategy = mock(ExecuteStepCandidacyStrategy.class);
    private final List<ExecuteStepCandidacyStrategy> strategies = List.of(this.strategy);
    private final StepRepository stepRepository = mock(StepRepository.class);
    private final ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
    private final ExecuteStepCandidacyUseCase executeStepCandidacyUseCase = new ExecuteStepCandidacyUseCase(this.strategies, this.stepRepository, this.applicationEventPublisher);

    private Candidate candidate;
    private ExecuteStepCandidacyDto executeStepCandidacyDto;
    private ExecuteStepCandidacyFindDto executeStepCandidacyFind;

    @BeforeEach
    void setUp() {
        this.candidate = TestUtils.dummyObject(Candidate.class);
        this.executeStepCandidacyDto = new ExecuteStepCandidacyDto(
                GeneratorIdentifier.forStep(),
                GeneratorIdentifier.forCandidacy(),
                GeneratorIdentifier.forSelectiveProcess(),
                TypeStep.THEORETICAL_TEST,
                null,
                null
        );
        this.executeStepCandidacyFind = new ExecuteStepCandidacyFindDto(
                GeneratorIdentifier.forStep(),
                StatusStepCandidacy.WAITING_FOR_EXECUTION,
                LocalDate.now().plusDays(3),
                LocalDate.now().minusDays(1)
        );
    }

    @Test
    void when_prompted_must_execute_successfully() {
        when(this.strategy.getTypeStep()).thenReturn(this.executeStepCandidacyDto.getType());
        when(this.stepRepository.find(
                this.executeStepCandidacyDto.getStepIdentifier(),
                this.executeStepCandidacyDto.getSelectiveProcessIdentifier(),
                this.executeStepCandidacyDto.getCandidacyIdentifier(),
                this.candidate.getIdentifier()
        )).thenReturn(Optional.of(this.executeStepCandidacyFind));

        assertDoesNotThrow(() -> this.executeStepCandidacyUseCase.execute(this.candidate, this.executeStepCandidacyDto));

        verify(this.stepRepository, times(1)).find(
                this.executeStepCandidacyDto.getStepIdentifier(),
                this.executeStepCandidacyDto.getSelectiveProcessIdentifier(),
                this.executeStepCandidacyDto.getCandidacyIdentifier(),
                this.candidate.getIdentifier()
        );
        verify(this.strategy, times(1)).getTypeStep();
        verify(this.strategy, times(1)).execute(this.candidate.getIdentifier(), this.executeStepCandidacyDto);
        verify(this.stepRepository, times(1)).updateStatusStepCandidacy(
                this.executeStepCandidacyDto.getStepIdentifier(),
                this.executeStepCandidacyDto.getCandidacyIdentifier(),
                StatusStepCandidacy.EXECUTED
        );
        verify(this.applicationEventPublisher, only()).publishEvent(
                new ExecuteStepCandidacy(
                        this.executeStepCandidacyDto.getStepIdentifier(),
                        this.executeStepCandidacyDto.getCandidacyIdentifier(),
                        this.candidate.getIdentifier(),
                        this.executeStepCandidacyDto.getSelectiveProcessIdentifier(),
                        this.executeStepCandidacyDto.getType().name()
                )
        );
        verifyNoMoreInteractions(this.strategy, this.stepRepository);
    }

    @Test
    void when_asked_should_throw_InternalErrorException_when_not_finding_a_strategy() {
        when(this.strategy.getTypeStep()).thenReturn(TypeStep.EXTERNAL);

        final InternalErrorException exception = assertThrows(
                InternalErrorException.class,
                () -> this.executeStepCandidacyUseCase.execute(this.candidate, this.executeStepCandidacyDto)
        );

        assertNotNull(exception);
        assertEquals(INTG_002, exception.getError());

        verify(this.strategy, only()).getTypeStep();
        verifyNoInteractions(this.applicationEventPublisher, this.stepRepository);
    }

}