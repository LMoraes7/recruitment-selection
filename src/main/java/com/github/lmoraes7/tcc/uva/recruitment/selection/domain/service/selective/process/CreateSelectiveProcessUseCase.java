package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.NewRegisteredSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.SelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto.SelectiveProcessDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.SelectiveProcessRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public final class CreateSelectiveProcessUseCase {
    private final StepRepository stepRepository;
    private final SelectiveProcessRepository selectiveProcessRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public CreateSelectiveProcessUseCase(
            final StepRepository stepRepository,
            final SelectiveProcessRepository selectiveProcessRepository,
            final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.stepRepository = stepRepository;
        this.selectiveProcessRepository = selectiveProcessRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public SelectiveProcess execute(final Employee employee, final SelectiveProcessDto dto) {
        final SelectiveProcess selectiveProcess = employee.createSelectiveProcess(
                this.stepRepository,
                this.selectiveProcessRepository,
                dto
        );

        this.applicationEventPublisher.publishEvent(new NewRegisteredSelectiveProcess(selectiveProcess.getIdentifier()));
        return selectiveProcess;
    }

}
