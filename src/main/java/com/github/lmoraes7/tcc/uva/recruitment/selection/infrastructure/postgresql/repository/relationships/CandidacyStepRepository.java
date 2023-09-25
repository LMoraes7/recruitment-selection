package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships;

import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.entity.StepCandidacyEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.batch.SaveCandidacyStepBatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.query.CandidacyStepCommands.SAVE;

@Repository
public class CandidacyStepRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CandidacyStepRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(final String candidacyIdentifier, final List<StepCandidacyEntity> stepsEntity) {
        this.jdbcTemplate.batchUpdate(
                SAVE.sql,
                new SaveCandidacyStepBatch(
                        candidacyIdentifier,
                        stepsEntity
                )
        );
    }

}
