package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.NewRegisteredSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.SelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Step;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.StepData;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto.SelectiveProcessDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto.SelectiveProcessStepDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.SelectiveProcessRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.StepRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

final class CreateSelectiveProcessUseCaseTest {
    private final StepRepository stepRepository = mock(StepRepository.class);
    private final SelectiveProcessRepository selectiveProcessRepository = mock(SelectiveProcessRepository.class);
    private final ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
    private final CreateSelectiveProcessUseCase createSelectiveProcessUseCase = new CreateSelectiveProcessUseCase(
            this.stepRepository,
            this.selectiveProcessRepository,
            this.applicationEventPublisher
    );

    private Employee employee;
    private SelectiveProcessDto selectiveProcessDto;
    private List<Step> steps;
    private SelectiveProcess selectiveProcess;

    @BeforeEach
    void setUp() {
        this.employee = dummyObject(Employee.class);
        this.selectiveProcessDto = new SelectiveProcessDto(
                dummyObject(String.class),
                dummyObject(String.class),
                dummyObject(String.class),
                Set.of(dummyObject(String.class)),
                Set.of(dummyObject(String.class)),
                Set.of(dummyObject(String.class)),
                List.of(
                        new SelectiveProcessStepDto(
                                GeneratorIdentifier.forStep(),
                                5767L
                        ),
                        new SelectiveProcessStepDto(
                                GeneratorIdentifier.forStep(),
                                345645L
                        ),
                        new SelectiveProcessStepDto(
                                GeneratorIdentifier.forStep(),
                                null
                        ),
                        new SelectiveProcessStepDto(
                                GeneratorIdentifier.forStep(),
                                65767L
                        )
                )
        );
        this.steps = this.selectiveProcessDto.getSteps().stream().map(it -> new ExternalStep(new StepData(it.getIdentifier(), TypeStep.UPLOAD_FILES))).collect(Collectors.toList());
        this.selectiveProcess = ConverterHelper.toModel(this.selectiveProcessDto);
    }

    @Test
    void when_requested_you_must_save_a_successful_selection_process() {
        final List<String> stepsIdentifiersToValidate = this.selectiveProcessDto.getSteps()
                .stream()
                .map(SelectiveProcessStepDto::getIdentifier)
                .toList();

        when(this.stepRepository.fetchSteps(stepsIdentifiersToValidate)).thenReturn(this.steps);
        when(this.selectiveProcessRepository.save(any(SelectiveProcess.class))).thenReturn(this.selectiveProcess);

        assertDoesNotThrow(() -> this.createSelectiveProcessUseCase.execute(this.employee, this.selectiveProcessDto));

        verify(this.stepRepository, only()).fetchSteps(stepsIdentifiersToValidate);
        verify(this.selectiveProcessRepository, only()).save(any(SelectiveProcess.class));
        verify(this.applicationEventPublisher, only()).publishEvent(new NewRegisteredSelectiveProcess(this.selectiveProcess.getIdentifier()));
    }
}