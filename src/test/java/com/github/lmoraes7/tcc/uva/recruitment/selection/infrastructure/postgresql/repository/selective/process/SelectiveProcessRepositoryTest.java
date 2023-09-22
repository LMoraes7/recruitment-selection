package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.SelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto.PaginationQuery;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto.SelectiveProcessoPaginated;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.SelectiveProcessStepRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.entity.SelectiveProcessEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.rowmapper.SelectiveProcessPageRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.rowmapper.SelectiveProcessRowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.converter.ConverterHelper.toEntity;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.query.SelectiveProcessCommands.*;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class SelectiveProcessRepositoryTest {
    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private final SelectiveProcessStepRepository selectiveProcessStepRepository = mock(SelectiveProcessStepRepository.class);
    private final SelectiveProcessRowMapper selectiveProcessRowMapper = mock(SelectiveProcessRowMapper.class);
    private final SelectiveProcessPageRowMapper selectiveProcessPageRowMapper = mock(SelectiveProcessPageRowMapper.class);
    private final SelectiveProcessRepository selectiveProcessRepository = new SelectiveProcessRepository(
            this.jdbcTemplate,
            this.selectiveProcessStepRepository,
            this.selectiveProcessRowMapper,
            this.selectiveProcessPageRowMapper
    );

    private SelectiveProcess selectiveProcess;
    private PaginationQuery paginationQuery;

    @BeforeEach
    void setUp() {
        this.selectiveProcess = new SelectiveProcess(
                GeneratorIdentifier.forSelectiveProcess(),
                dummyObject(String.class),
                dummyObject(String.class),
                dummyObject(String.class),
                dummyObject(StatusSelectiveProcess.class),
                Set.of(dummyObject(String.class)),
                Set.of(dummyObject(String.class)),
                Set.of(dummyObject(String.class)),
                List.of(dummyObject(ExternalStep.class), dummyObject(ExternalStep.class), dummyObject(ExternalStep.class), dummyObject(ExternalStep.class))
        );
        this.paginationQuery = new PaginationQuery(10, 20);
    }

    @Test
    void when_prompted_it_should_save_successfully() {
        final SelectiveProcessEntity entity = toEntity(this.selectiveProcess);

        when(this.jdbcTemplate.update(
                SAVE.sql,
                entity.getIdentifier(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getResponsibilities(),
                entity.getRequirements(),
                entity.getAdditionalInfos(),
                entity.getStatus(),
                entity.getDesiredPosition()
        )).thenReturn(1);

        assertDoesNotThrow(() -> this.selectiveProcessRepository.save(this.selectiveProcess));

        verify(this.jdbcTemplate, only()).update(
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
        verify(this.selectiveProcessStepRepository, only()).save(entity.getIdentifier(), entity.getSteps());
    }

    @Test
    void when_requested_to_search_by_id_the_entity_must_be_returned_successfully() {
        when(this.jdbcTemplate.queryForObject(
                FIND_BY_ID.sql,
                this.selectiveProcessRowMapper,
                this.selectiveProcess.getIdentifier()
        )).thenReturn(this.selectiveProcess);

        assertDoesNotThrow(() -> {
            final Optional<SelectiveProcess> optional = this.selectiveProcessRepository.findById(this.selectiveProcess.getIdentifier());

            assertNotNull(optional);
            assertTrue(optional.isPresent());
        });

        verify(this.jdbcTemplate, only()).queryForObject(
                FIND_BY_ID.sql,
                this.selectiveProcessRowMapper,
                this.selectiveProcess.getIdentifier()
        );
    }

    @Test
    void when_requested_to_search_by_id_it_should_not_return_the_entity() {
        when(this.jdbcTemplate.queryForObject(
                FIND_BY_ID.sql,
                this.selectiveProcessRowMapper,
                this.selectiveProcess.getIdentifier()
        )).thenThrow(EmptyResultDataAccessException.class);

        assertDoesNotThrow(() -> {
            final Optional<SelectiveProcess> optional = this.selectiveProcessRepository.findById(this.selectiveProcess.getIdentifier());

            assertNotNull(optional);
            assertTrue(optional.isEmpty());
        });

        verify(this.jdbcTemplate, only()).queryForObject(
                FIND_BY_ID.sql,
                this.selectiveProcessRowMapper,
                this.selectiveProcess.getIdentifier()
        );
    }

    @Test
    void when_requested_you_must_perform_a_paged_query_successfully() {
        final Pageable pageable = PageRequest.of(this.paginationQuery.getPageNumber(), this.paginationQuery.getPageSize());
        final PageImpl<SelectiveProcess> page = new PageImpl<>(List.of(this.selectiveProcess), pageable, 1);

        when(this.jdbcTemplate.query(
                FIND_ALL_OPEN.sql,
                this.selectiveProcessPageRowMapper,
                pageable.getPageSize(),
                pageable.getOffset()
        )).thenReturn(List.of(this.selectiveProcess));
        when(this.jdbcTemplate.queryForObject(COUNT_OPEN.sql, Integer.class)).thenReturn(1);

        assertDoesNotThrow(() -> {
            final SelectiveProcessoPaginated selectiveProcessoPaginated = this.selectiveProcessRepository.findAll(this.paginationQuery);

            assertEquals(selectiveProcessoPaginated.getSelectiveProcesses().size(), List.of(this.selectiveProcess).size());
            assertEquals(selectiveProcessoPaginated.getPageNumber(), page.getNumber());
            assertEquals(selectiveProcessoPaginated.getPageSize(), page.getSize());
            assertEquals(selectiveProcessoPaginated.getTotalPages(), page.getTotalPages());
            assertEquals(selectiveProcessoPaginated.getTotalElements(), page.getNumberOfElements());
            assertEquals(selectiveProcessoPaginated.getTotalResults(), page.getTotalElements());
        });

        verify(this.jdbcTemplate, times(1)).query(
                FIND_ALL_OPEN.sql,
                this.selectiveProcessPageRowMapper,
                pageable.getPageSize(),
                pageable.getOffset()
        );
        verify(this.jdbcTemplate, times(1)).queryForObject(COUNT_OPEN.sql, Integer.class);
        verifyNoMoreInteractions(this.jdbcTemplate);
    }

}