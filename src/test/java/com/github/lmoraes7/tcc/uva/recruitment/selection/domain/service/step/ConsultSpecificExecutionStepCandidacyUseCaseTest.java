package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.ConsultSpecificExecutionStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.InternalErrorException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ConsultSpecificStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.SpecificExecutionStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.consult.ConsultSpecificExecutionStepCandidacyStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.INTG_002;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class ConsultSpecificExecutionStepCandidacyUseCaseTest {
    private final ConsultSpecificExecutionStepCandidacyStrategy strategy = mock(ConsultSpecificExecutionStepCandidacyStrategy.class);
    private final List<ConsultSpecificExecutionStepCandidacyStrategy> strategies = List.of(this.strategy);
    private final ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
    private final ConsultSpecificExecutionStepCandidacyUseCase consultSpecificExecutionStepCandidacyUseCase = new ConsultSpecificExecutionStepCandidacyUseCase(this.strategies, applicationEventPublisher);

    private Candidate candidate;
    private ConsultSpecificStepCandidacyDto consultSpecificStepCandidacyDto;
    private SpecificExecutionStepCandidacyDto specificExecutionStepCandidacyDto;

    @BeforeEach
    void setUp() {
        this.candidate = dummyObject(Candidate.class);
        this.consultSpecificStepCandidacyDto = new ConsultSpecificStepCandidacyDto(
                GeneratorIdentifier.forStep(),
                GeneratorIdentifier.forCandidacy(),
                GeneratorIdentifier.forSelectiveProcess(),
                TypeStep.UPLOAD_FILES
        );
        this.specificExecutionStepCandidacyDto = dummyObject(SpecificExecutionStepCandidacyDto.class);
    }


    @Test
    void when_requested_must_consult_a_theoretical_step_successfully() {
        when(this.strategy.getTypeStep()).thenReturn(this.consultSpecificStepCandidacyDto.getType());
        when(this.strategy.execute(this.candidate.getIdentifier(), this.consultSpecificStepCandidacyDto))
                .thenReturn(this.specificExecutionStepCandidacyDto);

        assertDoesNotThrow(() -> this.consultSpecificExecutionStepCandidacyUseCase.execute(this.candidate, this.consultSpecificStepCandidacyDto));

        verify(this.strategy, times(1)).getTypeStep();
        verify(this.strategy, times(1)).execute(this.candidate.getIdentifier(), this.consultSpecificStepCandidacyDto);
        verify(this.applicationEventPublisher, only()).publishEvent(
                new ConsultSpecificExecutionStepCandidacy(
                        this.specificExecutionStepCandidacyDto.getStepIdentifier(),
                        this.specificExecutionStepCandidacyDto.getCandidacyIdentifier(),
                        this.specificExecutionStepCandidacyDto.getCandidateIdentifier(),
                        this.specificExecutionStepCandidacyDto.getSelectiveProcessIdentifier(),
                        this.consultSpecificStepCandidacyDto.getType().name()
                )
        );
        verifyNoMoreInteractions(this.strategy);
    }

    @Test
    void when_asked_should_throw_InternalErrorException_when_not_finding_a_strategy() {
        when(this.strategy.getTypeStep()).thenReturn(TypeStep.EXTERNAL);

        final InternalErrorException exception = assertThrows(
                InternalErrorException.class,
                () -> this.consultSpecificExecutionStepCandidacyUseCase.execute(this.candidate, this.consultSpecificStepCandidacyDto)
        );

        assertNotNull(exception);
        assertEquals(INTG_002, exception.getError());

        verify(this.strategy, only()).getTypeStep();
        verifyNoInteractions(this.applicationEventPublisher);
    }

}