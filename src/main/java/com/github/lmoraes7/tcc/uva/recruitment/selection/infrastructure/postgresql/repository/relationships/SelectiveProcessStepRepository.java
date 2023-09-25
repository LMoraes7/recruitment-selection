package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships;

import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.batch.SaveSelectiveProcessStepBatch;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.entity.StepSelectiveProcessEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.query.SelectiveProcessStepCommands.SAVE;

@Repository
public class SelectiveProcessStepRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SelectiveProcessStepRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(final String selectiveProcessIdentifier, final List<StepSelectiveProcessEntity> stepsEntity) {
        this.jdbcTemplate.batchUpdate(
                SAVE.sql,
                new SaveSelectiveProcessStepBatch(
                        selectiveProcessIdentifier,
                        stepsEntity
                )
        );
    }

}
