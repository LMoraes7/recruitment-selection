package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.entity.StepEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.query.StepCommands.SAVE;

@Repository
public class ExternalStepRepository {
    private final JdbcTemplate jdbcTemplate;

    public ExternalStepRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ExternalStep save(ExternalStep step) {
        final StepEntity entity = ConverterHelper.toEntity(step);

        this.jdbcTemplate.update(
                SAVE.sql,
                entity.getIdentifier(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getType()
        );

        return step;
    }

}
