package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event.NewRequestPasswordReset;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.PersonalRecordsEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.dto.RequestPasswordResetDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.CommonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class RequestPasswordResetUseCaseTest {
    private final CommonRepository commonRepository = mock(CommonRepository.class);
    private final ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
    private final RequestPasswordResetUseCase requestPasswordResetUseCase = new RequestPasswordResetUseCase(
            this.commonRepository,
            this.applicationEventPublisher
    );

    private RequestPasswordResetDto requestPasswordResetDto;
    private PersonalRecordsEntity personalRecordsEntity;

    @BeforeEach
    void setUp() {
        this.requestPasswordResetDto = dummyObject(RequestPasswordResetDto.class);
        this.personalRecordsEntity = dummyObject(PersonalRecordsEntity.class);
    }

    @Test
    void when_prompted_it_should_request_a_successful_password_reset() {
        when(this.commonRepository.findPersonalRecordsEntityByEmail(this.requestPasswordResetDto.getEmail()))
                .thenReturn(Optional.of(this.personalRecordsEntity));

        assertDoesNotThrow(() -> this.requestPasswordResetUseCase.execute(this.requestPasswordResetDto));

        verify(this.commonRepository, times(1))
                .findPersonalRecordsEntityByEmail(this.requestPasswordResetDto.getEmail());
        verify(this.commonRepository, times(1))
                .savePasswordChangeRequest(eq(this.personalRecordsEntity.getEmail()), eq(this.personalRecordsEntity.getTypeEntity()), any(String.class));
        verify(this.applicationEventPublisher, only())
                .publishEvent(new NewRequestPasswordReset(this.personalRecordsEntity.getEmail(), this.personalRecordsEntity.getTypeEntity()));
        verifyNoMoreInteractions(this.commonRepository);
    }

    @Test
    void when_prompted_should_throw_a_NotFoundException_when_requesting_a_password_reset() {
        when(this.commonRepository.findPersonalRecordsEntityByEmail(this.requestPasswordResetDto.getEmail()))
                .thenReturn(Optional.empty());

        final NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> this.requestPasswordResetUseCase.execute(this.requestPasswordResetDto)
        );

        assertEquals(exception.getCode(), this.requestPasswordResetDto.getEmail());

        verify(this.commonRepository, only())
                .findPersonalRecordsEntityByEmail(this.requestPasswordResetDto.getEmail());
        verifyNoInteractions(this.applicationEventPublisher);
    }

}