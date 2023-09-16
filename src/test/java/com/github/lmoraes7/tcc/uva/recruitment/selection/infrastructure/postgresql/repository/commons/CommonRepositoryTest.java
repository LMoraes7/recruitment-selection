package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorResetPasswordCode;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.PasswordChangeRequest;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.PersonalRecordsEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.entity.vo.TypeEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.rowmapper.PasswordChangeRequestRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.rowmapper.PersonalRecordsEntityRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.properties.ConfigPasswordChangeRequestProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.query.CommonCommands.*;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class CommonRepositoryTest {

    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private final ConfigPasswordChangeRequestProperties configPasswordChangeRequestProperties = mock(ConfigPasswordChangeRequestProperties.class);
    private final PasswordChangeRequestRowMapper passwordChangeRequestRowMapper = mock(PasswordChangeRequestRowMapper.class);
    public final PersonalRecordsEntityRowMapper personalRecordsEntityRowMapper = mock(PersonalRecordsEntityRowMapper.class);
    private final CommonRepository commonRepository = new CommonRepository(
            this.jdbcTemplate,
            this.configPasswordChangeRequestProperties,
            this.passwordChangeRequestRowMapper,
            this.personalRecordsEntityRowMapper
    );

    private Employee employee;
    private String code;
    private Long limitTime;

    @BeforeEach
    void setUp() {
        employee = dummyObject(Employee.class);
        code = GeneratorResetPasswordCode.execute();
        limitTime = 10L;
    }

    @Test
    void when_prompted_should_save_records_successfully() {
        when(this.jdbcTemplate.update(
                SAVE_RECORDS.sql,
                employee.getPersonalData().getEmail(),
                employee.getPersonalData().getCpf(),
                TypeEntity.fromValue(com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeEntity.EMP).name()
        )).thenReturn(1);

        assertDoesNotThrow(
                () -> this.commonRepository.saveRecords(
                        this.employee.getPersonalData().getEmail(),
                        this.employee.getPersonalData().getCpf(),
                        com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeEntity.EMP
                )
        );

        verify(this.jdbcTemplate, only()).update(
                SAVE_RECORDS.sql,
                employee.getPersonalData().getEmail(),
                employee.getPersonalData().getCpf(),
                TypeEntity.fromValue(com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeEntity.EMP).name()
        );
        verifyNoInteractions(this.configPasswordChangeRequestProperties, this.passwordChangeRequestRowMapper);
    }

    @Test
    void when_prompted_should_save_password_reset_request_successfully() {
        when(this.configPasswordChangeRequestProperties.getLimitTime()).thenReturn(this.limitTime);
        when(this.jdbcTemplate.update(
                eq(SAVE_PASSWORD_CHANGE_REQUEST.sql),
                eq(code),
                eq(this.employee.getPersonalData().getEmail()),
                eq(TypeEntity.fromValue(com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeEntity.EMP).name()),
                any(LocalDateTime.class),
                any(LocalDateTime.class),
                eq(false)
        )).thenReturn(1);

        assertDoesNotThrow(
                () -> this.commonRepository.savePasswordChangeRequest(
                        this.employee.getPersonalData().getEmail(),
                        com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeEntity.EMP,
                        this.code
                )
        );

        verify(this.configPasswordChangeRequestProperties, only()).getLimitTime();
        verify(this.jdbcTemplate, only()).update(
                eq(SAVE_PASSWORD_CHANGE_REQUEST.sql),
                eq(code),
                eq(this.employee.getPersonalData().getEmail()),
                eq(TypeEntity.fromValue(com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeEntity.EMP).name()),
                any(LocalDateTime.class),
                any(LocalDateTime.class),
                eq(false)
        );
        verifyNoInteractions(this.passwordChangeRequestRowMapper);
    }

    @Test
    void when_prompted_should_fetch_a_successful_password_reset_request() {
        when(this.jdbcTemplate.queryForObject(
                FIND_REQUEST_CHANGE_BY_CODE.sql,
                this.passwordChangeRequestRowMapper,
                this.code
        )).thenReturn(dummyObject(PasswordChangeRequest.class));

        assertDoesNotThrow(() -> {
            final Optional<PasswordChangeRequest> optional = this.commonRepository.findPasswordChangeRequestByCode(this.code);

            assertNotNull(optional);
            assertTrue(optional.isPresent());
        });

        verify(this.jdbcTemplate, only()).queryForObject(
                FIND_REQUEST_CHANGE_BY_CODE.sql,
                this.passwordChangeRequestRowMapper,
                this.code
        );
        verifyNoInteractions(this.configPasswordChangeRequestProperties, this.passwordChangeRequestRowMapper);
    }

    @Test
    void when_prompted_should_fetch_a_failed_password_reset_request() {
        when(this.jdbcTemplate.queryForObject(
                FIND_REQUEST_CHANGE_BY_CODE.sql,
                this.passwordChangeRequestRowMapper,
                this.code
        )).thenThrow(EmptyResultDataAccessException.class);

        assertDoesNotThrow(() -> {
            final Optional<PasswordChangeRequest> optional = this.commonRepository.findPasswordChangeRequestByCode(this.code);

            assertNotNull(optional);
            assertTrue(optional.isEmpty());
        });

        verify(this.jdbcTemplate, only()).queryForObject(
                FIND_REQUEST_CHANGE_BY_CODE.sql,
                this.passwordChangeRequestRowMapper,
                this.code
        );
        verifyNoInteractions(this.configPasswordChangeRequestProperties, this.passwordChangeRequestRowMapper);
    }

    @Test
    void when_prompted_must_update_the_use_of_the_password_reset_request() {
        when(this.jdbcTemplate.update(
                CLOSE_BY_CODE.sql,
                this.code
        )).thenReturn(1);

        assertDoesNotThrow(() -> this.commonRepository.closePasswordChangeRequest(this.code));

        verify(this.jdbcTemplate, only()).update(
                CLOSE_BY_CODE.sql,
                this.code
        );
        verifyNoInteractions(this.configPasswordChangeRequestProperties, this.passwordChangeRequestRowMapper);
    }


    @Test
    void when_prompted_should_fetch_a_successful_personal_records_entity() {
        when(this.jdbcTemplate.queryForObject(
                FIND_REGISTER_ENTITY_BY_EMAIL.sql,
                this.personalRecordsEntityRowMapper,
                this.employee.getPersonalData().getEmail()
        )).thenReturn(dummyObject(PersonalRecordsEntity.class));

        assertDoesNotThrow(() -> {
            final Optional<PersonalRecordsEntity> optional = this.commonRepository.findPersonalRecordsEntityByEmail(this.employee.getPersonalData().getEmail());

            assertNotNull(optional);
            assertTrue(optional.isPresent());
        });

        verify(this.jdbcTemplate, only()).queryForObject(
                FIND_REGISTER_ENTITY_BY_EMAIL.sql,
                this.personalRecordsEntityRowMapper,
                this.employee.getPersonalData().getEmail()
        );
        verifyNoInteractions(this.configPasswordChangeRequestProperties, this.passwordChangeRequestRowMapper);
    }

    @Test
    void when_prompted_should_fetch_a_failed_personal_records_entity() {
        when(this.jdbcTemplate.queryForObject(
                FIND_REGISTER_ENTITY_BY_EMAIL.sql,
                this.personalRecordsEntityRowMapper,
                this.employee.getPersonalData().getEmail()
        )).thenThrow(EmptyResultDataAccessException.class);

        assertDoesNotThrow(() -> {
            final Optional<PersonalRecordsEntity> optional = this.commonRepository.findPersonalRecordsEntityByEmail(this.employee.getPersonalData().getEmail());

            assertNotNull(optional);
            assertTrue(optional.isEmpty());
        });

        verify(this.jdbcTemplate, only()).queryForObject(
                FIND_REGISTER_ENTITY_BY_EMAIL.sql,
                this.personalRecordsEntityRowMapper,
                this.employee.getPersonalData().getEmail()
        );
        verifyNoInteractions(this.configPasswordChangeRequestProperties, this.passwordChangeRequestRowMapper);
    }

}