package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.PasswordChangeRequest;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.PersonalRecordsEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.entity.vo.TypeEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.rowmapper.PasswordChangeRequestRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.rowmapper.PersonalRecordsEntityRowMapper;
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
    public final PersonalRecordsEntityRowMapper personalRecordsEntityRowMapper;

    @Autowired
    public CommonRepository(
            final JdbcTemplate jdbcTemplate,
            final ConfigPasswordChangeRequestProperties configPasswordChangeRequestProperties,
            final PasswordChangeRequestRowMapper passwordChangeRequestRowMapper,
            final PersonalRecordsEntityRowMapper personalRecordsEntityRowMapper
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.configPasswordChangeRequestProperties = configPasswordChangeRequestProperties;
        this.passwordChangeRequestRowMapper = passwordChangeRequestRowMapper;
        this.personalRecordsEntityRowMapper = personalRecordsEntityRowMapper;
    }

    public void saveRecords(final String email, final String cpf, final com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeEntity typeEntity) {
        this.jdbcTemplate.update(
                SAVE_RECORDS.sql,
                email,
                cpf,
                TypeEntity.fromValue(typeEntity).name()
        );
    }

    public Optional<PersonalRecordsEntity> findPersonalRecordsEntityByEmail(final String email) {
        PersonalRecordsEntity personalRecordsEntity = null;
        try {
            personalRecordsEntity = this.jdbcTemplate.queryForObject(
                    FIND_REGISTER_ENTITY_BY_EMAIL.sql,
                    this.personalRecordsEntityRowMapper,
                    email
            );
        } catch (final EmptyResultDataAccessException ignored) {
        }

        return Optional.ofNullable(personalRecordsEntity);
    }

    public void savePasswordChangeRequest(final String email, final com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeEntity typeEntity, final String code) {
        final LocalDateTime requestedIn = LocalDateTime.now();

        this.jdbcTemplate.update(
                SAVE_PASSWORD_CHANGE_REQUEST.sql,
                code,
                email,
                TypeEntity.fromValue(typeEntity).name(),
                requestedIn,
                requestedIn.plusSeconds(this.configPasswordChangeRequestProperties.getLimitTime()),
                false
        );
    }

    public Optional<PasswordChangeRequest> findPasswordChangeRequestByCode(final String code) {
        PasswordChangeRequest passwordChangeRequest = null;
        try {
            passwordChangeRequest = this.jdbcTemplate.queryForObject(
                    FIND_REQUEST_CHANGE_BY_CODE.sql,
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
