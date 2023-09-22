package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.ConsultSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.SelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.SelectiveProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public final class ConsultSpecificSelectiveProcessUseCase {
    private final SelectiveProcessRepository selectiveProcessRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public ConsultSpecificSelectiveProcessUseCase(
            final SelectiveProcessRepository selectiveProcessRepository,
            final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.selectiveProcessRepository = selectiveProcessRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public SelectiveProcess execute(final String identifier) {
        final SelectiveProcess selectiveProcess = this.selectiveProcessRepository.findById(identifier)
                .orElseThrow(() -> new NotFoundException(identifier, SelectiveProcess.class));

        this.applicationEventPublisher.publishEvent(
            new ConsultSelectiveProcess(
                    selectiveProcess.getIdentifier(),
                    selectiveProcess.getStatus().name()
            )
        );
        return selectiveProcess;
    }

}
