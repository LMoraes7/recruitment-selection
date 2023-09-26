package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.ConsultCandidacyPaginated;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.CandidacyPaginated;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.PaginationQuery;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.CandidacyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

final class ConsultListOfCandidacyUseCaseTest {
    private final CandidacyRepository candidacyRepository = mock(CandidacyRepository.class);
    private final ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
    private final ConsultListOfCandidacyUseCase consultListOfCandidacyUseCase = new ConsultListOfCandidacyUseCase(this.candidacyRepository, this.applicationEventPublisher);

    private Candidate candidate;
    private PaginationQuery paginationQuery;
    private CandidacyPaginated candidacyPaginated;

    @BeforeEach
    void setUp() {
        this.candidate = dummyObject(Candidate.class);
        this.paginationQuery = new PaginationQuery(10, 20);
        this.candidacyPaginated = dummyObject(CandidacyPaginated.class);
    }

    @Test
    void when_requested_you_must_perform_a_paged_query_successfully() {
        when(this.candidacyRepository.findAll(this.candidate.getIdentifier(), this.paginationQuery))
                .thenReturn(this.candidacyPaginated);

        assertDoesNotThrow(() -> this.consultListOfCandidacyUseCase.execute(this.candidate, this.paginationQuery));

        verify(this.candidacyRepository, only()).findAll(this.candidate.getIdentifier(), this.paginationQuery);
        verify(this.applicationEventPublisher, only()).publishEvent(new ConsultCandidacyPaginated(this.candidate.getIdentifier()));
    }

}