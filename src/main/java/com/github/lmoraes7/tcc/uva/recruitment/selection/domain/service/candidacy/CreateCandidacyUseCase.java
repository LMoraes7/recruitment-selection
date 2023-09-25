package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.NewRegisteredCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.CandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.CandidacyRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.SelectiveProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public final class CreateCandidacyUseCase {
    private final SelectiveProcessRepository selectiveProcessRepository;
    private final CandidacyRepository candidacyRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public CreateCandidacyUseCase(
            final SelectiveProcessRepository selectiveProcessRepository,
            final CandidacyRepository candidacyRepository,
            final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.selectiveProcessRepository = selectiveProcessRepository;
        this.candidacyRepository = candidacyRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public Candidacy execute(final Candidate candidate, final CandidacyDto dto) {
        final Candidacy candidacy = candidate.performCandidacy(
                this.selectiveProcessRepository,
                this.candidacyRepository,
                dto
        );

        this.applicationEventPublisher.publishEvent(
            new NewRegisteredCandidacy(
                    candidacy.getIdentifier(),
                    candidate.getIdentifier(),
                    dto.getSelectiveProcessIdentifier()
            )
        );
        return candidacy;
    }

}
