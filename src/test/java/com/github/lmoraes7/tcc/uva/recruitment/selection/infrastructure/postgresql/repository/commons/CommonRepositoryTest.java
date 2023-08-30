package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.entity.vo.TypeEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.properties.ConfigPasswordChangeRequestProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.query.CommonCommands.SAVE_PASSWORD_CHANGE_REQUEST;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.query.CommonCommands.SAVE_RECORDS;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

final class CommonRepositoryTest {

    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private final ConfigPasswordChangeRequestProperties configPasswordChangeRequestProperties = mock(ConfigPasswordChangeRequestProperties.class);
    private final CommonRepository commonRepository = new CommonRepository(this.jdbcTemplate, this.configPasswordChangeRequestProperties);

    private Employee employee;
    private String code;
    private Long limitTime;

    @BeforeEach
    void setUp() {
        employee = dummyObject(Employee.class);
        code = UUID.randomUUID().toString();
        limitTime = 10L;
    }

    @Test
    void when_prompted_should_save_records_successfully() {
        when(this.jdbcTemplate.update(
                SAVE_RECORDS.sql,
                employee.getPersonalData().getEmail(),
                employee.getPersonalData().getCpf()
        )).thenReturn(1);

        assertDoesNotThrow(
                () -> this.commonRepository.saveRecords(
                        this.employee.getPersonalData().getEmail(),
                        this.employee.getPersonalData().getCpf()
                )
        );

        verify(this.jdbcTemplate, only()).update(
                SAVE_RECORDS.sql,
                employee.getPersonalData().getEmail(),
                employee.getPersonalData().getCpf()
        );
        verifyNoInteractions(this.configPasswordChangeRequestProperties);
    }

    @Test
    void when_prompted_should_save_password_reset_request_successfully() {
        when(this.configPasswordChangeRequestProperties.getLimitTime()).thenReturn(this.limitTime);
        when(this.jdbcTemplate.update(
                SAVE_PASSWORD_CHANGE_REQUEST.sql,
                code,
                employee.getPersonalData().getEmail(),
                TypeEntity.fromValue(employee.getClass()).name(),
                this.limitTime,
                false
        )).thenReturn(1);

        assertDoesNotThrow(() -> this.commonRepository.savePasswordChangeRequest(this.employee, this.code));

        verify(this.configPasswordChangeRequestProperties, only()).getLimitTime();
        verify(this.jdbcTemplate, only()).update(
                SAVE_PASSWORD_CHANGE_REQUEST.sql,
                code,
                employee.getPersonalData().getEmail(),
                TypeEntity.fromValue(employee.getClass()).name(),
                this.limitTime,
                false
        );
    }

}