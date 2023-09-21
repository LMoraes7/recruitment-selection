package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.SelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.SelectiveProcessStepRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.entity.SelectiveProcessEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.converter.ConverterHelper.toEntity;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.query.SelectiveProcessCommands.SAVE;

@Repository
public class SelectiveProcessRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SelectiveProcessStepRepository selectiveProcessStepRepository;

    @Autowired
    public SelectiveProcessRepository(
            final JdbcTemplate jdbcTemplate,
            final SelectiveProcessStepRepository selectiveProcessStepRepository
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.selectiveProcessStepRepository = selectiveProcessStepRepository;
    }

    public SelectiveProcess save(final SelectiveProcess selectiveProcess) {
        final SelectiveProcessEntity entity = toEntity(selectiveProcess);

        this.jdbcTemplate.update(
                SAVE.sql,
                entity.getIdentifier(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getResponsibilities(),
                entity.getRequirements(),
                entity.getAdditionalInfos(),
                entity.getStatus(),
                entity.getDesiredPosition()
        );

        this.selectiveProcessStepRepository.save(entity.getIdentifier(), entity.getSteps());
        return selectiveProcess;
    }

}
