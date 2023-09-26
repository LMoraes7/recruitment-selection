package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.ConsultCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.SpecificCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.CandidacyRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public final class ConsultSpecificCandidacyUseCase {
    private final CandidacyRepository candidacyRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ConsultSpecificCandidacyUseCase(
            final CandidacyRepository candidacyRepository,
            final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.candidacyRepository = candidacyRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public SpecificCandidacyDto execute(final Candidate candidate, final String candidacyIdentifier) {
        final SpecificCandidacyDto candidacy = candidate.findSpecificCandidacy(this.candidacyRepository, candidacyIdentifier);

        this.applicationEventPublisher.publishEvent(
                new ConsultCandidacy(
                        candidacyIdentifier,
                        candidacy.getStatus().name(),
                        candidacy.getSelectiveProcess().getId(),
                        candidate.getIdentifier()
                )
        );
        return candidacy;
    }

}
