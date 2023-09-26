package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.CloseCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.CloseCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.CandidacyRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public final class CloseCandidacyUseCase {
    private final CandidacyRepository candidacyRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public CloseCandidacyUseCase(
            final CandidacyRepository candidacyRepository,
            final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.candidacyRepository = candidacyRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void execute(final Candidate candidate, final CloseCandidacyDto dto) {
        candidate.closeCandidacy(this.candidacyRepository, dto);

        this.applicationEventPublisher.publishEvent(
                new CloseCandidacy(
                        dto.getCandidacyIdentifier(),
                        candidate.getIdentifier(),
                        dto.getSelectiveProcessIdentifier()
                )
        );
    }

}
