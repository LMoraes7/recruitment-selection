package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.ConsultCandidacyPaginated;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.CandidacyPaginated;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.PaginationQuery;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.CandidacyRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public final class ConsultListOfCandidacyUseCase {
    private final CandidacyRepository candidacyRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ConsultListOfCandidacyUseCase(
            final CandidacyRepository candidacyRepository,
            final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.candidacyRepository = candidacyRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public CandidacyPaginated execute(final Candidate candidate, final PaginationQuery paginationQuery) {
        final CandidacyPaginated candidacies = candidate.findCandidacies(this.candidacyRepository, paginationQuery);

        this.applicationEventPublisher.publishEvent(new ConsultCandidacyPaginated(candidate.getIdentifier()));
        return candidacies;
    }

}
