package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.RealeaseStepForCandidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.FindStepsDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ReleaseStepForCandidateDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.CandidacyStepRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.ExternalStepCandidacyRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.List;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class ReleaseStepForCandidateUseCaseTest {
    private final CandidacyStepRepository candidacyStepRepository = mock(CandidacyStepRepository.class);
    private final ExternalStepCandidacyRepository externalStepCandidacyRepository = mock(ExternalStepCandidacyRepository.class);
    private final ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
    private final ReleaseStepForCandidateUseCase releaseStepForCandidateUseCase = new ReleaseStepForCandidateUseCase(
            this.candidacyStepRepository,
            this.externalStepCandidacyRepository,
            this.applicationEventPublisher
    );

    private Employee employee;
    private ReleaseStepForCandidateDto releaseStepForCandidateDto;
    private List<FindStepsDto> findStepsDtos;

    @BeforeEach
    void setUp() {
        this.employee = TestUtils.dummyObject(Employee.class);
        this.releaseStepForCandidateDto = new ReleaseStepForCandidateDto("7654321", GeneratorIdentifier.forCandidacy(), dummyObject(String.class), LocalDateTime.now());
        this.findStepsDtos = List.of(
                new FindStepsDto("1234567", "7654321", StatusStepCandidacy.COMPLETED, TypeStep.EXTERNAL),
                new FindStepsDto("7654321", null, StatusStepCandidacy.BLOCKED, TypeStep.EXTERNAL)
        );
    }

    @Test
    void when_requested_it_must_release_the_step_successfully() {
        when(this.candidacyStepRepository.getSteps(
                this.releaseStepForCandidateDto.getCandidacyIdentifier(),
                this.releaseStepForCandidateDto.getStepIdentifier()
        )).thenReturn(this.findStepsDtos);

        assertDoesNotThrow(() -> this.releaseStepForCandidateUseCase.execute(
                this.employee,
                this.releaseStepForCandidateDto
        ));

        verify(this.candidacyStepRepository, times(1)).getSteps(
                this.releaseStepForCandidateDto.getCandidacyIdentifier(),
                this.releaseStepForCandidateDto.getStepIdentifier()
        );
        verify(this.candidacyStepRepository, times(1)).updateStatus(
                this.releaseStepForCandidateDto.getCandidacyIdentifier(),
                this.releaseStepForCandidateDto.getStepIdentifier(),
                StatusStepCandidacy.WAITING_FOR_EXECUTION
        );
        verify(this.externalStepCandidacyRepository, only()).updateData(
                this.releaseStepForCandidateDto.getCandidacyIdentifier(),
                this.releaseStepForCandidateDto.getStepIdentifier(),
                this.releaseStepForCandidateDto.getLink(),
                this.releaseStepForCandidateDto.getDateTime()
        );
        verify(this.applicationEventPublisher, only()).publishEvent(
                new RealeaseStepForCandidate(this.releaseStepForCandidateDto.getCandidacyIdentifier(), this.releaseStepForCandidateDto.getStepIdentifier())
        );
        verifyNoMoreInteractions(this.candidacyStepRepository);
    }

}