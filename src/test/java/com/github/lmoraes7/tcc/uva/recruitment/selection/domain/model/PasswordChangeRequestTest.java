package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorResetPasswordCode;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_005;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_006;
import static org.junit.jupiter.api.Assertions.*;

final class PasswordChangeRequestTest {
    private PasswordChangeRequest passwordChangeRequest;

    @BeforeEach
    void setUp() {
        this.passwordChangeRequest = TestUtils.dummyObject(PasswordChangeRequest.class);
    }

    @Test
    void when_requested_it_should_not_throw_any_Exception_when_validating_if_the_code_has_already_been_used() {
        this.passwordChangeRequest = new PasswordChangeRequest(
                GeneratorResetPasswordCode.execute(),
                UUID.randomUUID().toString(),
                TypeEntity.EMP,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                false
        );

        assertDoesNotThrow(() -> this.passwordChangeRequest.hasItAlreadyBeenUsed());
    }

    @Test
    void when_requested_it_should_not_throw_any_Exception_when_validating_if_the_code_is_already_expired() {
        this.passwordChangeRequest = new PasswordChangeRequest(
                GeneratorResetPasswordCode.execute(),
                UUID.randomUUID().toString(),
                TypeEntity.EMP,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                false
        );

        assertDoesNotThrow(() -> this.passwordChangeRequest.isItExpired());
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

        final BusinessException exception = assertThrows(
                BusinessException.class,
                () -> this.passwordChangeRequest.hasItAlreadyBeenUsed()
        );

        assertNotNull(exception);
        assertEquals(APIX_005, exception.getError());
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

        final BusinessException exception = assertThrows(
                BusinessException.class,
                () -> this.passwordChangeRequest.isItExpired()
        );

        assertNotNull(exception);
        assertEquals(APIX_006, exception.getError());
    }

}