package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.RealeaseStepForCandidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ReleaseStepForCandidateDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.CandidacyStepRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.ExternalStepCandidacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public final class ReleaseStepForCandidateUseCase {
    private final CandidacyStepRepository candidacyStepRepository;
    private final ExternalStepCandidacyRepository externalStepCandidacyRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public ReleaseStepForCandidateUseCase(
            final CandidacyStepRepository candidacyStepRepository,
            final ExternalStepCandidacyRepository externalStepCandidacyRepository,
            final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.candidacyStepRepository = candidacyStepRepository;
        this.externalStepCandidacyRepository = externalStepCandidacyRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void execute(final Employee employee, final ReleaseStepForCandidateDto dto) {
        employee.releaseStepForCandidate(
                this.candidacyStepRepository,
                this.externalStepCandidacyRepository,
                dto
        );

        this.applicationEventPublisher.publishEvent(new RealeaseStepForCandidate(dto.getCandidacyIdentifier(), dto.getStepIdentifier()));
    }

}
