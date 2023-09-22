package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.ConsultSelectiveProcessPagineted;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto.PaginationQuery;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto.SelectiveProcessoPaginated;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.SelectiveProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public final class ConsultListOfSelectiveProcessUseCase {
    private final SelectiveProcessRepository selectiveProcessRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public ConsultListOfSelectiveProcessUseCase(
            final SelectiveProcessRepository selectiveProcessRepository,
            final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.selectiveProcessRepository = selectiveProcessRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public SelectiveProcessoPaginated execute(final PaginationQuery paginationQuery) {
        final SelectiveProcessoPaginated selectiveProcessoPaginated = this.selectiveProcessRepository.findAll(paginationQuery);

        this.applicationEventPublisher.publishEvent(new ConsultSelectiveProcessPagineted());
        return selectiveProcessoPaginated;
    }

}
