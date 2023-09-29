package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Step;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ExecuteStepCandidacyFindDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.StepFindRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.StepWithIdAndTypeRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.vo.StepFindVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.SELECT_IDENTIFIERS_IN;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.FIND;

@Repository
public class StepRepository {
    private final JdbcTemplate jdbcTemplate;
    private final StepWithIdAndTypeRowMapper stepWithIdAndTypeRowMapper;
    private final StepFindRowMapper stepFindRowMapper;

    @Autowired
    public StepRepository(
            final JdbcTemplate jdbcTemplate,
            final StepWithIdAndTypeRowMapper stepWithIdAndTypeRowMapper,
            final StepFindRowMapper stepFindRowMapper
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.stepWithIdAndTypeRowMapper = stepWithIdAndTypeRowMapper;
        this.stepFindRowMapper = stepFindRowMapper;
    }

    public Collection<Step> fetchSteps(final Collection<String> identifiers) {
        final String inSql = String.join(",", Collections.nCopies(identifiers.size(), "?"));
        return this.jdbcTemplate.query(
                String.format(SELECT_IDENTIFIERS_IN.sql, inSql),
                this.stepWithIdAndTypeRowMapper,
                identifiers.toArray()
        );
    }

    public Optional<ExecuteStepCandidacyFindDto> find(
            final String stepIdentifier,
            final String selectiveProcessIdentifier,
            final String candidacyIdentifier,
            final String candidateIdentifier
    ) {
        StepFindVo result = null;

        try {
            result = this.jdbcTemplate.queryForObject(
                    FIND.sql,
                    this.stepFindRowMapper,
                    stepIdentifier,
                    selectiveProcessIdentifier,
                    candidacyIdentifier,
                    candidateIdentifier
            );
        } catch (final EmptyResultDataAccessException ignored) {
            return Optional.empty();
        }

        return Optional.ofNullable(ConverterHelper.toDto(result));
    }

}
