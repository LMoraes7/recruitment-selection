package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.CloseCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.CloseCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.CandidacyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

final class CloseCandidacyUseCaseTest {
    private final CandidacyRepository candidacyRepository = mock(CandidacyRepository.class);
    private final ApplicationEventPublisher applicationEventPublisher = Mockito.mock(ApplicationEventPublisher.class);
    private final CloseCandidacyUseCase closeCandidacyUseCase = new CloseCandidacyUseCase(this.candidacyRepository, this.applicationEventPublisher);

    private Candidate candidate;
    private CloseCandidacyDto closeCandidacyDto;

    @BeforeEach
    void setUp() {
        this.candidate = dummyObject(Candidate.class);
        this.closeCandidacyDto = dummyObject(CloseCandidacyDto.class);
    }

    @Test
    void when_prompted_you_must_successfully_close_an_application() {
        assertDoesNotThrow(() -> this.closeCandidacyUseCase.execute(this.candidate, this.closeCandidacyDto));

        verify(this.candidacyRepository, only()).closeCandidacy(
                this.candidate.getIdentifier(),
                this.closeCandidacyDto.getSelectiveProcessIdentifier(),
                this.closeCandidacyDto.getCandidacyIdentifier()
        );
        verify(this.applicationEventPublisher, only()).publishEvent(
                new CloseCandidacy(
                        this.closeCandidacyDto.getCandidacyIdentifier(),
                        this.candidate.getIdentifier(),
                        this.closeCandidacyDto.getSelectiveProcessIdentifier()
                )
        );
    }

}