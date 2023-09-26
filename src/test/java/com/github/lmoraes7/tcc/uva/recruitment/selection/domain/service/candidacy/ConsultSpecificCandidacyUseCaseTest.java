package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.ConsultCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.SpecificCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.CandidacyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class ConsultSpecificCandidacyUseCaseTest {
    private final CandidacyRepository candidacyRepository = mock(CandidacyRepository.class);
    private final ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
    private final ConsultSpecificCandidacyUseCase consultSpecificCandidacyUseCase = new ConsultSpecificCandidacyUseCase(this.candidacyRepository, this.applicationEventPublisher);

    private Candidate candidate;
    private String candidacyIdentifier;
    private SpecificCandidacyDto specificCandidacyDto;

    @BeforeEach
    void setUp() {
        this.candidate = dummyObject(Candidate.class);
        this.candidacyIdentifier = dummyObject(String.class);
        this.specificCandidacyDto = dummyObject(SpecificCandidacyDto.class);
    }

    @Test
    void when_requested_you_must_consult_an_application_by_ID_successfully() {
        when(this.candidacyRepository.findById(
                this.candidate.getIdentifier(),
                this.candidacyIdentifier
        )).thenReturn(Optional.of(this.specificCandidacyDto));

        assertDoesNotThrow(() -> this.consultSpecificCandidacyUseCase.execute(this.candidate, this.candidacyIdentifier));

        verify(this.candidacyRepository, only()).findById(
                this.candidate.getIdentifier(),
                this.candidacyIdentifier
        );
        verify(this.applicationEventPublisher, only()).publishEvent(
                new ConsultCandidacy(
                        this.candidacyIdentifier,
                        this.specificCandidacyDto.getStatus().name(),
                        this.specificCandidacyDto.getSelectiveProcess().getId(),
                        this.candidate.getIdentifier()
                )
        );
    }

    @Test
    void when_prompted_should_fail_to_search_for_an_application_by_id() {
        when(this.candidacyRepository.findById(
                this.candidate.getIdentifier(),
                this.candidacyIdentifier
        )).thenReturn(Optional.empty());

        final NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> this.consultSpecificCandidacyUseCase.execute(this.candidate, this.candidacyIdentifier)
        );

        assertEquals(exception.getCode(), this.candidacyIdentifier);
        assertEquals(exception.getClassType(), Candidacy.class);

        verify(this.candidacyRepository, only()).findById(
                this.candidate.getIdentifier(),
                this.candidacyIdentifier
        );
        verifyNoInteractions(this.applicationEventPublisher);
    }
}