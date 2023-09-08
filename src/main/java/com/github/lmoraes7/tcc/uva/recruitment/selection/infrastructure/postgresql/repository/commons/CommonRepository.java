package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.PasswordChangeRequest;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.entity.vo.TypeEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.rowmapper.PasswordChangeRequestRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.properties.ConfigPasswordChangeRequestProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.query.CommonCommands.*;

@Repository
public class CommonRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ConfigPasswordChangeRequestProperties configPasswordChangeRequestProperties;
    public final PasswordChangeRequestRowMapper passwordChangeRequestRowMapper;

    @Autowired
    public CommonRepository(
            final JdbcTemplate jdbcTemplate,
            final ConfigPasswordChangeRequestProperties configPasswordChangeRequestProperties,
            final PasswordChangeRequestRowMapper passwordChangeRequestRowMapper
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.configPasswordChangeRequestProperties = configPasswordChangeRequestProperties;
        this.passwordChangeRequestRowMapper = passwordChangeRequestRowMapper;
    }

    public void saveRecords(final String email, final String cpf) {
        this.jdbcTemplate.update(
                SAVE_RECORDS.sql,
                email,
                cpf
        );
    }

    public void savePasswordChangeRequest(final Employee employee, final String code) {
        final LocalDateTime requestedIn = LocalDateTime.now();

        this.jdbcTemplate.update(
                SAVE_PASSWORD_CHANGE_REQUEST.sql,
                code,
                employee.getPersonalData().getEmail(),
                TypeEntity.fromValue(employee.getClass()).name(),
                requestedIn,
                requestedIn.plusSeconds(this.configPasswordChangeRequestProperties.getLimitTime()),
                false
        );
    }

    public Optional<PasswordChangeRequest> findPasswordChangeRequestByCode(final String code) {
        PasswordChangeRequest passwordChangeRequest = null;
        try {
            passwordChangeRequest = this.jdbcTemplate.queryForObject(
                    FIND_BY_CODE.sql,
                    this.passwordChangeRequestRowMapper,
                    code
            );
        } catch (final EmptyResultDataAccessException ignored) {
        }

        return Optional.ofNullable(passwordChangeRequest);
    }

    public void closePasswordChangeRequest(final String code) {
        this.jdbcTemplate.update(
                CLOSE_BY_CODE.sql,
                code
        );
    }

}
