package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.CloseSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.CandidacyRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.SelectiveProcessRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

final class CloseSelectiveProcessUseCaseTest {
    private final SelectiveProcessRepository selectiveProcessRepository = mock(SelectiveProcessRepository.class);
    private final CandidacyRepository candidacyRepository = mock(CandidacyRepository.class);
    private final ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
    private final CloseSelectiveProcessUseCase closeSelectiveProcessUseCase = new CloseSelectiveProcessUseCase(this.selectiveProcessRepository, this.candidacyRepository, this.applicationEventPublisher);

    private Employee employee;
    private String selectiveProcessIdentifier;

    @BeforeEach
    void setUp() {
        this.employee = dummyObject(Employee.class);
        this.selectiveProcessIdentifier = GeneratorIdentifier.forSelectiveProcess();
    }

    @Test
    void when_requested_you_must_successfully_complete_a_selection_process() {
        assertDoesNotThrow(() -> this.closeSelectiveProcessUseCase.execute(this.employee, this.selectiveProcessIdentifier));

        verify(this.selectiveProcessRepository, only()).updateStatus(this.selectiveProcessIdentifier, StatusSelectiveProcess.CLOSED);
        verify(this.candidacyRepository, only()).closeCandidacyBySelectiveProcess(this.selectiveProcessIdentifier);
        verify(this.applicationEventPublisher, only()).publishEvent(new CloseSelectiveProcess(this.selectiveProcessIdentifier));
    }
}