package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.NewRegisteredStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.InternalErrorException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Step;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.StepDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.create.CreateStepStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Collection;
import java.util.List;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.INTG_002;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class CreateStepUseCaseTest {
    private final CreateStepStrategy strategy = mock(CreateStepStrategy.class);
    private final Collection<CreateStepStrategy> createStepStrategies = List.of(this.strategy);
    private final ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);;
    private final CreateStepUseCase createStepUseCase = new CreateStepUseCase(this.createStepStrategies, this.applicationEventPublisher);

    private Employee employee;
    private StepDto stepDto;
    private Step step;

    @BeforeEach
    void setUp() {
        this.employee = dummyObject(Employee.class);
        this.stepDto = new StepDto(
                "title",
                "description",
                TypeStep.EXTERNAL,
                null,
                null
        );
        this.step = dummyObject(ExternalStep.class);
    }

    @Test
    void when_prompted_should_create_a_step_successfully() {
        when(this.strategy.getTypeStep()).thenReturn(this.stepDto.getType());
        when(this.strategy.execute(this.stepDto)).thenReturn(this.step);

        assertDoesNotThrow(() -> this.createStepUseCase.execute(this.employee, this.stepDto));

        verify(this.strategy, times(1)).getTypeStep();
        verify(this.strategy, times(1)).execute(this.stepDto);
        verify(this.applicationEventPublisher, only()).publishEvent(
                new NewRegisteredStep(
                        this.step.getData().getIdentifier(),
                        this.step.getData().getType()
                )
        );
        verifyNoMoreInteractions(this.strategy);
    }

    @Test
    void when_requested_it_should_throw_an_InternalErrorException_when_there_is_no_suitable_strategy() {
        when(this.strategy.getTypeStep()).thenReturn(TypeStep.UPLOAD_FILES);

        final InternalErrorException exception = assertThrows(
                InternalErrorException.class,
                () -> this.createStepUseCase.execute(this.employee, this.stepDto)
        );

        assertEquals(INTG_002, exception.getError());

        verify(this.strategy, only()).getTypeStep();
        verifyNoInteractions(this.applicationEventPublisher);
    }

}