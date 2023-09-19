package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.TheoricalTestStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.batch.SaveTheoricalTestStepBatch;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.entity.TheoricalTestStepEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.SAVE;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.SAVE_THEORICAL_DATA;

@Repository
public class TheoricalTestlStepRepository {
    private final JdbcTemplate jdbcTemplate;

    public TheoricalTestlStepRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public TheoricalTestStep save(TheoricalTestStep step) {
        final TheoricalTestStepEntity entity = ConverterHelper.toEntity(step);

        this.jdbcTemplate.update(
                SAVE.sql,
                entity.getIdentifier(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getType()
        );

        this.jdbcTemplate.batchUpdate(
                SAVE_THEORICAL_DATA.sql,
                new SaveTheoricalTestStepBatch(
                        entity.getIdentifier(),
                        entity.getQuestionIdentifiers()
                )
        );

        return step;
    }

}
