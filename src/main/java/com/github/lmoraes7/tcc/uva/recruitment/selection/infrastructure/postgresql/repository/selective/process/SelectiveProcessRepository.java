package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.SelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto.PaginationQuery;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto.SelectiveProcessoPaginated;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.SelectiveProcessStepRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.entity.SelectiveProcessEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.rowmapper.SelectiveProcessPageRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.rowmapper.SelectiveProcessRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.rowmapper.SelectiveProcessWithStepsRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.rowmapper.vo.SelectiveProcessStepsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.converter.ConverterHelper.toDomain;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.converter.ConverterHelper.toEntity;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.query.SelectiveProcessCommands.*;

@Repository
public class SelectiveProcessRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SelectiveProcessStepRepository selectiveProcessStepRepository;
    private final SelectiveProcessRowMapper selectiveProcessRowMapper;
    private final SelectiveProcessPageRowMapper selectiveProcessPageRowMapper;
    private final SelectiveProcessWithStepsRowMapper selectiveProcessWithStepsRowMapper;

    @Autowired
    public SelectiveProcessRepository(
            final JdbcTemplate jdbcTemplate,
            final SelectiveProcessStepRepository selectiveProcessStepRepository,
            final SelectiveProcessRowMapper selectiveProcessRowMapper,
            final SelectiveProcessPageRowMapper selectiveProcessPageRowMapper,
            final SelectiveProcessWithStepsRowMapper selectiveProcessWithStepsRowMapper
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.selectiveProcessStepRepository = selectiveProcessStepRepository;
        this.selectiveProcessRowMapper = selectiveProcessRowMapper;
        this.selectiveProcessPageRowMapper = selectiveProcessPageRowMapper;
        this.selectiveProcessWithStepsRowMapper = selectiveProcessWithStepsRowMapper;
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

    public Optional<SelectiveProcess> findWithStepsById(final String identifier) {
        final List<SelectiveProcessStepsVo> result = this.jdbcTemplate.query(
                FIND_WITH_STEPS_BY_ID.sql,
                this.selectiveProcessWithStepsRowMapper,
                identifier
        );

        if (result.isEmpty())
            return Optional.empty();

        return Optional.of(toDomain(result));
    }

    public SelectiveProcessoPaginated findAll(final PaginationQuery paginationQuery) {
        final Pageable pageable = PageRequest.of(paginationQuery.getPageNumber(), paginationQuery.getPageSize());

        final List<SelectiveProcess> selectiveProcesses = this.jdbcTemplate.query(
                FIND_ALL_OPEN.sql,
                this.selectiveProcessPageRowMapper,
                pageable.getPageSize(),
                pageable.getOffset()
        );

        final PageImpl<SelectiveProcess> page = new PageImpl<>(
                selectiveProcesses,
                pageable,
                this.jdbcTemplate.queryForObject(COUNT_OPEN.sql, Integer.class)
        );

        return new SelectiveProcessoPaginated(
                selectiveProcesses,
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getNumberOfElements(),
                page.getTotalElements()
        );
    }

    public void updateStatus(final String selectiveProcessIdentifier, final StatusSelectiveProcess status) {
        int update = this.jdbcTemplate.update(
                UPDATE_STATUS.sql,
                status.name(),
                selectiveProcessIdentifier
        );

        if (update == 0)
            throw new NotFoundException(selectiveProcessIdentifier, SelectiveProcess.class);
    }

}