package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorResetPasswordCode;
import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.NewRequestPasswordReset;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.PersonalRecordsEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.dto.RequestPasswordResetDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.CommonRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public final class RequestPasswordResetUseCase {
    private final CommonRepository commonRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public RequestPasswordResetUseCase(
            final CommonRepository commonRepository,
            final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.commonRepository = commonRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void execute(final RequestPasswordResetDto dto) {
        PersonalRecordsEntity personalRecords = this.commonRepository.findPersonalRecordsEntityByEmail(dto.getEmail())
                .orElseThrow(() -> new NotFoundException(dto.getEmail(), PersonalRecordsEntity.class));

        this.commonRepository.savePasswordChangeRequest(
                personalRecords.getEmail(),
                personalRecords.getTypeEntity(),
                GeneratorResetPasswordCode.execute()
        );

        this.applicationEventPublisher.publishEvent(
                new NewRequestPasswordReset(
                        personalRecords.getEmail(),
                        personalRecords.getTypeEntity())
        );
    }

}
