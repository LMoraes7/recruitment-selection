package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.consult;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.StepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ConsultSpecificStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.SpecificExecutionStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.ExternalStepCandidacyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class ConsultExternalExecutionStepCandidacyTest {
    private final ExternalStepCandidacyRepository externalStepCandidacyRepository = mock(ExternalStepCandidacyRepository.class);
    private final ConsultExternalExecutionStepCandidacy consultTheoricalTestExecutionStepCandidacy = new ConsultExternalExecutionStepCandidacy(this.externalStepCandidacyRepository);

    private String candidateIdentifier;
    private ConsultSpecificStepCandidacyDto consultSpecificStepCandidacyDto;
    private SpecificExecutionStepCandidacyDto specificExecutionStepCandidacyDto;

    @BeforeEach
    void setUp() {
        this.candidateIdentifier = GeneratorIdentifier.forCandidate();
        this.consultSpecificStepCandidacyDto = dummyObject(ConsultSpecificStepCandidacyDto.class);
        this.specificExecutionStepCandidacyDto = dummyObject(SpecificExecutionStepCandidacyDto.class);
    }

    @Test
    void when_prompted_it_should_return_the_correct_type() {
        assertEquals(TypeStep.EXTERNAL, this.consultTheoricalTestExecutionStepCandidacy.getTypeStep());
    }

    @Test
    void when_requested_must_consult_a_theoretical_step_successfully() {
        when(this.externalStepCandidacyRepository.find(
                this.consultSpecificStepCandidacyDto.getCandidacyIdentifier(),
                this.candidateIdentifier,
                this.consultSpecificStepCandidacyDto.getSelectiveProcessIdentifier(),
                this.consultSpecificStepCandidacyDto.getStepIdentifier()
        )).thenReturn(Optional.of(this.specificExecutionStepCandidacyDto));

        assertDoesNotThrow(() -> this.consultTheoricalTestExecutionStepCandidacy.execute(this.candidateIdentifier, this.consultSpecificStepCandidacyDto));

        verify(this.externalStepCandidacyRepository, only()).find(
                this.consultSpecificStepCandidacyDto.getCandidacyIdentifier(),
                this.candidateIdentifier,
                this.consultSpecificStepCandidacyDto.getSelectiveProcessIdentifier(),
                this.consultSpecificStepCandidacyDto.getStepIdentifier()
        );
    }

    @Test
    void when_prompted_must_fail_to_query_a_theoretical_step() {
        when(this.externalStepCandidacyRepository.find(
                this.consultSpecificStepCandidacyDto.getCandidacyIdentifier(),
                this.candidateIdentifier,
                this.consultSpecificStepCandidacyDto.getSelectiveProcessIdentifier(),
                this.consultSpecificStepCandidacyDto.getStepIdentifier()
        )).thenReturn(Optional.empty());

        final NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> this.consultTheoricalTestExecutionStepCandidacy.execute(this.candidateIdentifier, this.consultSpecificStepCandidacyDto)
        );

        assertEquals(exception.getCode(), this.consultSpecificStepCandidacyDto.getStepIdentifier());
        assertEquals(exception.getClassType(), StepCandidacy.class);

        verify(this.externalStepCandidacyRepository, only()).find(
                this.consultSpecificStepCandidacyDto.getCandidacyIdentifier(),
                this.candidateIdentifier,
                this.consultSpecificStepCandidacyDto.getSelectiveProcessIdentifier(),
                this.consultSpecificStepCandidacyDto.getStepIdentifier()
        );
    }
}