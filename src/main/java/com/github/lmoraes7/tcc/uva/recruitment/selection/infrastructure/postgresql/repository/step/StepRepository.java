package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Step;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.StepWithIdAndTypeRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.SELECT_IDENTIFIERS_IN;

@Repository
public class StepRepository {
    private final JdbcTemplate jdbcTemplate;
    private final StepWithIdAndTypeRowMapper stepWithIdAndTypeRowMapper;

    @Autowired
    public StepRepository(
            final JdbcTemplate jdbcTemplate,
            final StepWithIdAndTypeRowMapper stepWithIdAndTypeRowMapper
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.stepWithIdAndTypeRowMapper = stepWithIdAndTypeRowMapper;
    }

    public Collection<Step> fetchSteps(final Collection<String> identifiers) {
        final String inSql = String.join(",", Collections.nCopies(identifiers.size(), "?"));
        return this.jdbcTemplate.query(
                String.format(SELECT_IDENTIFIERS_IN.sql, inSql),
                this.stepWithIdAndTypeRowMapper,
                identifiers.toArray()
        );
    }

}
