package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.ConsultSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.SelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.StepData;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.SelectiveProcessRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class ConsultSpecificSelectiveProcessUseCaseTest {
    private final SelectiveProcessRepository selectiveProcessRepository = mock(SelectiveProcessRepository.class);
    private final ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
    private final ConsultSpecificSelectiveProcessUseCase consultSpecificSelectiveProcessUseCase = new ConsultSpecificSelectiveProcessUseCase(this.selectiveProcessRepository, this.applicationEventPublisher);

    private SelectiveProcess selectiveProcess;

    @BeforeEach
    void setUp() {
        this.selectiveProcess = new SelectiveProcess(
                GeneratorIdentifier.forSelectiveProcess(),
                "Title",
                "Description",
                "Desired Position",
                StatusSelectiveProcess.IN_PROGRESS,
                Set.of("responsibilities 1", "responsibilities 2", "responsibilities 3"),
                Set.of("requirements 1", "requirements 2", "requirements 3"),
                Set.of("additional Infos 1", "additional Infos 2", "additional Infos 3"),
                List.of(
                        new ExternalStep(
                                new StepData("STE-123456789"),
                                null
                        ),
                        new ExternalStep(
                                new StepData("STE-987654321"),
                                445675678L
                        ),
                        new ExternalStep(
                                new StepData("STE-564326567"),
                                8934756L
                        )
                )
        );
    }

    @Test
    void when_requested_to_search_by_id_it_must_return_the_entity() {
        when(this.selectiveProcessRepository.findById(this.selectiveProcess.getIdentifier()))
                .thenReturn(Optional.of(this.selectiveProcess));

        assertDoesNotThrow(() -> this.consultSpecificSelectiveProcessUseCase.execute(this.selectiveProcess.getIdentifier()));

        verify(this.selectiveProcessRepository, only()).findById(this.selectiveProcess.getIdentifier());
        verify(this.applicationEventPublisher, only()).publishEvent(new ConsultSelectiveProcess(this.selectiveProcess.getIdentifier(), this.selectiveProcess.getStatus().name()));
    }

    @Test
    void when_requested_to_search_by_id_it_must_throw_a_NotFoundException() {
        when(this.selectiveProcessRepository.findById(this.selectiveProcess.getIdentifier()))
                .thenReturn(Optional.empty());

        final NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> this.consultSpecificSelectiveProcessUseCase.execute(this.selectiveProcess.getIdentifier())
        );

        assertEquals(exception.getCode(), this.selectiveProcess.getIdentifier());
        assertEquals(exception.getClassType(), SelectiveProcess.class);

        verify(this.selectiveProcessRepository, only()).findById(this.selectiveProcess.getIdentifier());
    }

}