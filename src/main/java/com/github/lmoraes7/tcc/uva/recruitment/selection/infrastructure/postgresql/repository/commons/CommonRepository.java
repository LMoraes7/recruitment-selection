package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.entity.vo.TypeEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.properties.ConfigPasswordChangeRequestProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.query.CommonCommands.SAVE_PASSWORD_CHANGE_REQUEST;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.query.CommonCommands.SAVE_RECORDS;

@Repository
public class CommonRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ConfigPasswordChangeRequestProperties configPasswordChangeRequestProperties;

    public CommonRepository(
            final JdbcTemplate jdbcTemplate,
            final ConfigPasswordChangeRequestProperties configPasswordChangeRequestProperties
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.configPasswordChangeRequestProperties = configPasswordChangeRequestProperties;
    }

    public void saveRecords(final String email, final String cpf) {
        this.jdbcTemplate.update(
                SAVE_RECORDS.sql,
                email,
                cpf
        );
    }

    public void savePasswordChangeRequest(final Employee employee, final String code) {
        this.jdbcTemplate.update(
                SAVE_PASSWORD_CHANGE_REQUEST.sql,
                code,
                employee.getPersonalData().getEmail(),
                TypeEntity.fromValue(employee.getClass()).name(),
                this.configPasswordChangeRequestProperties.getLimitTime(),
                false
        );
    }

}
