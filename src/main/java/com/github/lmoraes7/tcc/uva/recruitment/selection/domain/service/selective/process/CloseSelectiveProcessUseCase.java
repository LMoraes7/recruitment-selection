package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.CloseSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.CandidacyRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.SelectiveProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public final class CloseSelectiveProcessUseCase {
    private final SelectiveProcessRepository selectiveProcessRepository;
    private final CandidacyRepository candidacyRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public CloseSelectiveProcessUseCase(
            final SelectiveProcessRepository selectiveProcessRepository,
            final CandidacyRepository candidacyRepository,
            final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.selectiveProcessRepository = selectiveProcessRepository;
        this.candidacyRepository = candidacyRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void execute(final Employee employee, final String selectiveProcessIdentifier) {
        employee.closeSelectiveProcess(
                this.selectiveProcessRepository,
                this.candidacyRepository,
                selectiveProcessIdentifier
        );

        this.applicationEventPublisher.publishEvent(new CloseSelectiveProcess(selectiveProcessIdentifier));
    }

}
