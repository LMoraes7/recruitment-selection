package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.PasswordReset;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.InternalErrorException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.PasswordChangeRequest;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.dto.RedefinePasswordDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.strategy.RedefinePasswordStrategy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_023;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.INTG_002;

@Service
public final class RedefinePasswordUseCase {

    private final CommonRepository commonRepository;
    private final Collection<RedefinePasswordStrategy> redefinePasswordStrategies;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public RedefinePasswordUseCase(
            final CommonRepository commonRepository,
            final Collection<RedefinePasswordStrategy> redefinePasswordStrategies,
            final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.commonRepository = commonRepository;
        this.redefinePasswordStrategies = redefinePasswordStrategies;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void execute(final RedefinePasswordDto dto) {
        final PasswordChangeRequest passwordChangeRequest = this.commonRepository.findPasswordChangeRequestByCode(dto.getCode())
                .orElseThrow(() -> new BusinessException(APIX_023, "Código informado é inexistente."));

        passwordChangeRequest.hasItAlreadyBeenUsed();
        passwordChangeRequest.isItExpired();

        this.redefinePasswordStrategies.stream()
                .filter(it -> it.getTypeEntity() == passwordChangeRequest.getTypeEntity())
                .findFirst()
                .orElseThrow(() -> new InternalErrorException(INTG_002))
                .execute(passwordChangeRequest, dto);

        this.commonRepository.closePasswordChangeRequest(passwordChangeRequest.getCode());
        this.applicationEventPublisher.publishEvent(new PasswordReset(passwordChangeRequest.getEmailEntity()));
    }

}
