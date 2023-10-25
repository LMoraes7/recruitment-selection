package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorResetPasswordCode;
import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.PasswordReset;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.InternalErrorException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.PasswordChangeRequest;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.dto.RedefinePasswordDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.strategy.RedefinePasswordStrategy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.CommonRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class RedefinePasswordUseCaseTest {
    private final RedefinePasswordStrategy redefinePasswordStrategy = mock(RedefinePasswordStrategy.class);

    private final CommonRepository commonRepository = mock(CommonRepository.class);
    private final Collection<RedefinePasswordStrategy> redefinePasswordStrategies = List.of(redefinePasswordStrategy);
    private final ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
    private final RedefinePasswordUseCase redefinePasswordUseCase = new RedefinePasswordUseCase(
            this.commonRepository,
            this.redefinePasswordStrategies,
            this.applicationEventPublisher
    );

    private RedefinePasswordDto redefinePasswordDto;
    private PasswordChangeRequest passwordChangeRequest;

    @BeforeEach
    void setUp() {
        this.redefinePasswordDto = TestUtils.dummyObject(RedefinePasswordDto.class);

        this.passwordChangeRequest = new PasswordChangeRequest(
                GeneratorResetPasswordCode.execute(),
                UUID.randomUUID().toString(),
                TypeEntity.EMP,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                false
        );
    }

    @Test
    void when_prompted_should_reset_a_password_successfully() {
        when(this.commonRepository.findPasswordChangeRequestByCode(this.redefinePasswordDto.getCode()))
                .thenReturn(Optional.ofNullable(this.passwordChangeRequest));
        when(this.redefinePasswordStrategy.getTypeEntity()).thenReturn(this.passwordChangeRequest.getTypeEntity());

        assertDoesNotThrow(() -> this.redefinePasswordUseCase.execute(this.redefinePasswordDto));

        verify(this.commonRepository, times(1)).findPasswordChangeRequestByCode(this.redefinePasswordDto.getCode());
        verify(this.redefinePasswordStrategy, times(1)).getTypeEntity();
        verify(this.redefinePasswordStrategy, times(1)).execute(this.passwordChangeRequest, this.redefinePasswordDto);
        verify(this.commonRepository, times(1)).closePasswordChangeRequest(this.passwordChangeRequest.getCode());
        verify(this.applicationEventPublisher, only()).publishEvent(new PasswordReset(this.passwordChangeRequest.getEmailEntity()));
        verifyNoMoreInteractions(this.commonRepository, this.redefinePasswordStrategy);
    }

    @Test
    void when_asked_should_throw_a_NotFoundException_when_it_doesnt_find_the_code() {
        when(this.commonRepository.findPasswordChangeRequestByCode(this.redefinePasswordDto.getCode()))
                .thenReturn(Optional.empty());

        final NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> this.redefinePasswordUseCase.execute(this.redefinePasswordDto)
        );

        assertNotNull(exception);
        assertEquals(this.redefinePasswordDto.getCode(), exception.getCode());
        assertEquals(PasswordChangeRequest.class, exception.getClassType());

        verify(this.commonRepository, only()).findPasswordChangeRequestByCode(this.redefinePasswordDto.getCode());
        verifyNoInteractions(this.redefinePasswordStrategy, this.applicationEventPublisher);
    }

    @Test
    void when_asked_it_should_throw_an_InternalErrorException_when_it_doesnt_find_the_strategy() {
        when(this.commonRepository.findPasswordChangeRequestByCode(this.redefinePasswordDto.getCode()))
                .thenReturn(Optional.ofNullable(this.passwordChangeRequest));
        when(this.redefinePasswordStrategy.getTypeEntity()).thenReturn(TypeEntity.CAN);

        final InternalErrorException exception = assertThrows(
                InternalErrorException.class,
                () -> this.redefinePasswordUseCase.execute(this.redefinePasswordDto)
        );

        assertNotNull(exception);
        assertEquals(INTG_002, exception.getError());

        verify(this.commonRepository, only()).findPasswordChangeRequestByCode(this.redefinePasswordDto.getCode());
        verify(this.redefinePasswordStrategy, only()).getTypeEntity();
        verifyNoInteractions(this.applicationEventPublisher);
    }

    @Test
    void when_requested_it_should_a_BusinessException_when_the_code_has_already_been_used() {
        this.passwordChangeRequest = new PasswordChangeRequest(
                GeneratorResetPasswordCode.execute(),
                UUID.randomUUID().toString(),
                TypeEntity.EMP,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                true
        );

        when(this.commonRepository.findPasswordChangeRequestByCode(this.redefinePasswordDto.getCode()))
                .thenReturn(Optional.ofNullable(this.passwordChangeRequest));

        final BusinessException exception = assertThrows(
                BusinessException.class,
                () -> this.redefinePasswordUseCase.execute(this.redefinePasswordDto)
        );

        assertNotNull(exception);
        assertEquals(APIX_005, exception.getError());

        verify(this.commonRepository, only()).findPasswordChangeRequestByCode(this.redefinePasswordDto.getCode());
        verifyNoInteractions(this.redefinePasswordStrategy, this.applicationEventPublisher);
    }

    @Test
    void when_requested_it_should_a_BusinessException_when_the_code_has_already_expired() {
        this.passwordChangeRequest = new PasswordChangeRequest(
                GeneratorResetPasswordCode.execute(),
                UUID.randomUUID().toString(),
                TypeEntity.EMP,
                LocalDateTime.now(),
                LocalDateTime.now(),
                false
        );

        when(this.commonRepository.findPasswordChangeRequestByCode(this.redefinePasswordDto.getCode()))
                .thenReturn(Optional.ofNullable(this.passwordChangeRequest));

        final BusinessException exception = assertThrows(
                BusinessException.class,
                () -> this.redefinePasswordUseCase.execute(this.redefinePasswordDto)
        );

        assertNotNull(exception);
        assertEquals(APIX_006, exception.getError());

        verify(this.commonRepository, only()).findPasswordChangeRequestByCode(this.redefinePasswordDto.getCode());
        verifyNoInteractions(this.redefinePasswordStrategy, this.applicationEventPublisher);
    }

}