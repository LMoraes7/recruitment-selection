package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.FindStepsDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.entity.StepCandidacyEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.batch.SaveCandidacyStepBatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.query.CandidacyStepCommands.*;

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

    public List<FindStepsDto> getSteps(final String candidacyIdentifier, final String stepIdentifier) {
        return this.jdbcTemplate.query(
                SELECT_STEPS.sql,
                (rs, rowNumber) -> new FindStepsDto(
                        rs.getString("step_identifier"),
                        rs.getString("next_step_identifier"),
                        StatusStepCandidacy.valueOf(rs.getString("step_status")),
                        TypeStep.valueOf(rs.getString("step_type"))
                ),
                candidacyIdentifier,
                stepIdentifier,
                stepIdentifier
        );
    }

    public void updateStatus(
            final String candidacyIdentifier,
            final String stepIdentifier,
            final StatusStepCandidacy status
    ) {
        this.jdbcTemplate.update(
                UPDATE_STATUS.sql,
                status.name(),
                candidacyIdentifier,
                stepIdentifier
        );
    }

}
