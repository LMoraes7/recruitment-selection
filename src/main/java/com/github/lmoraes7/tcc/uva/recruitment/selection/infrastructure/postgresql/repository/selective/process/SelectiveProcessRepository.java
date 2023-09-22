package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.SelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.SelectiveProcessStepRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.entity.SelectiveProcessEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.rowmapper.SelectiveProcessRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.converter.ConverterHelper.toEntity;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.query.SelectiveProcessCommands.FIND_BY_ID;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.query.SelectiveProcessCommands.SAVE;

@Repository
public class SelectiveProcessRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SelectiveProcessStepRepository selectiveProcessStepRepository;
    private final SelectiveProcessRowMapper selectiveProcessRowMapper;

    @Autowired
    public SelectiveProcessRepository(
            final JdbcTemplate jdbcTemplate,
            final SelectiveProcessStepRepository selectiveProcessStepRepository,
            final SelectiveProcessRowMapper selectiveProcessRowMapper
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.selectiveProcessStepRepository = selectiveProcessStepRepository;
        this.selectiveProcessRowMapper = selectiveProcessRowMapper;
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

    public Optional<SelectiveProcess> findById(final String identifier) {
        SelectiveProcess selectiveProcess = null;
        try {
            selectiveProcess = this.jdbcTemplate.queryForObject(
                    FIND_BY_ID.sql,
                    this.selectiveProcessRowMapper,
                    identifier
            );
        } catch (final EmptyResultDataAccessException ignored) {
        }

        return Optional.ofNullable(selectiveProcess);
    }

}
