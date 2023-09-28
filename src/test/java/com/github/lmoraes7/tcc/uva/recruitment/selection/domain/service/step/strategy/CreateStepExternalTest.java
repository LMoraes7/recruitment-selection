package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.StepDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.create.CreateStepExternal;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.ExternalStepRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

final class CreateStepExternalTest {
    private final ExternalStepRepository externalStepRepository = mock(ExternalStepRepository.class);
    private final CreateStepExternal createStepExternal = new CreateStepExternal(this.externalStepRepository);

    private StepDto stepDto;
    private ExternalStep externalStep;

    @BeforeEach
    void setUp() {
        this.stepDto = dummyObject(StepDto.class);
        this.externalStep = dummyObject(ExternalStep.class);
    }

    @Test
    void when_prompted_should_create_a_step_successfully() {
        when(this.externalStepRepository.save(any(ExternalStep.class))).thenReturn(this.externalStep);

        assertDoesNotThrow(() -> this.createStepExternal.execute(this.stepDto));

        verify(this.externalStepRepository, only()).save(any(ExternalStep.class));
    }

    @Test
    void when_requested_it_must_return_the_type_of_step_to_be_treated() {
        assertDoesNotThrow(() -> assertEquals(TypeStep.EXTERNAL, this.createStepExternal.getTypeStep()));

        verifyNoInteractions(this.externalStepRepository);
    }

}