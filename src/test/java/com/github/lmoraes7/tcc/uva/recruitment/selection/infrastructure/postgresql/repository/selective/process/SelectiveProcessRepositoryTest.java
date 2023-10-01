package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.SelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.StepSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto.PaginationQuery;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto.SelectiveProcessoPaginated;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.SelectiveProcessStepRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.entity.SelectiveProcessEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.rowmapper.SelectiveProcessPageRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.rowmapper.SelectiveProcessRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.rowmapper.SelectiveProcessWithStepsRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.rowmapper.vo.SelectiveProcessStepsVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

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
    private final SelectiveProcessWithStepsRowMapper selectiveProcessWithStepsRowMapper = mock(SelectiveProcessWithStepsRowMapper.class);
    private final SelectiveProcessRepository selectiveProcessRepository = new SelectiveProcessRepository(
            this.jdbcTemplate,
            this.selectiveProcessStepRepository,
            this.selectiveProcessRowMapper,
            this.selectiveProcessPageRowMapper,
            this.selectiveProcessWithStepsRowMapper
    );

    private SelectiveProcess selectiveProcess;
    private PaginationQuery paginationQuery;
    private List<SelectiveProcessStepsVo> selectiveProcessStepsVos;

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
        this.selectiveProcessStepsVos = Arrays.asList(
                new SelectiveProcessStepsVo(
                        this.selectiveProcess.getIdentifier(),
                        this.selectiveProcess.getStatus(),
                        "STE-123456784",
                        "STE-123456785",
                        null,
                        TypeStep.EXTERNAL
                ),
                new SelectiveProcessStepsVo(
                        this.selectiveProcess.getIdentifier(),
                        this.selectiveProcess.getStatus(),
                        "STE-123456785",
                        null,
                        589765L,
                        TypeStep.UPLOAD_FILES
                ),
                new SelectiveProcessStepsVo(
                        this.selectiveProcess.getIdentifier(),
                        this.selectiveProcess.getStatus(),
                        "STE-123456782",
                        "STE-123456783",
                        895765L,
                        TypeStep.THEORETICAL_TEST
                ),
                new SelectiveProcessStepsVo(
                        this.selectiveProcess.getIdentifier(),
                        this.selectiveProcess.getStatus(),
                        "STE-123456783",
                        "STE-123456784",
                        6789687L,
                        TypeStep.THEORETICAL_TEST
                ),
                new SelectiveProcessStepsVo(
                        this.selectiveProcess.getIdentifier(),
                        this.selectiveProcess.getStatus(),
                        "STE-123456781",
                        "STE-123456782",
                        1234324L,
                        TypeStep.UPLOAD_FILES
                )
        );
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

    @Test
    void when_requested_you_must_successfully_complete_the_selection_process_with_the_steps() {
        for (int i = 0; i < 10; i++) {
            Collections.shuffle(this.selectiveProcessStepsVos);

            when(this.jdbcTemplate.query(
                    FIND_WITH_STEPS_BY_ID.sql,
                    this.selectiveProcessWithStepsRowMapper,
                    this.selectiveProcess.getIdentifier()
            )).thenReturn(this.selectiveProcessStepsVos);

            assertDoesNotThrow(() -> {
                final Optional<SelectiveProcess> optional = this.selectiveProcessRepository.findWithStepsById(this.selectiveProcess.getIdentifier());

                assertTrue(optional.isPresent());

                final List<StepSelectiveProcess> steps = optional.get().getSteps();

                assertNotNull(steps);
                assertEquals(this.selectiveProcessStepsVos.size(), steps.size());
                assertEquals("STE-123456781", steps.get(0).getData().getIdentifier());
                assertEquals("STE-123456782", steps.get(1).getData().getIdentifier());
                assertEquals("STE-123456783", steps.get(2).getData().getIdentifier());
                assertEquals("STE-123456784", steps.get(3).getData().getIdentifier());
                assertNull(steps.get(3).getLimitTime());
                assertEquals("STE-123456785", steps.get(4).getData().getIdentifier());
            });
        }

        verify(this.jdbcTemplate, times(10)).query(
                FIND_WITH_STEPS_BY_ID.sql,
                this.selectiveProcessWithStepsRowMapper,
                this.selectiveProcess.getIdentifier()
        );
        verifyNoMoreInteractions(this.jdbcTemplate);
    }

    @Test
    void when_requested_must_fail_to_pursue_the_selection_process_with_the_steps() {
        when(this.jdbcTemplate.query(
                FIND_WITH_STEPS_BY_ID.sql,
                this.selectiveProcessWithStepsRowMapper,
                this.selectiveProcess.getIdentifier()
        )).thenReturn(List.of());

        assertDoesNotThrow(() -> {
            final Optional<SelectiveProcess> optional = this.selectiveProcessRepository.findWithStepsById(this.selectiveProcess.getIdentifier());

            assertTrue(optional.isEmpty());
        });

        verify(this.jdbcTemplate, only()).query(
                FIND_WITH_STEPS_BY_ID.sql,
                this.selectiveProcessWithStepsRowMapper,
                this.selectiveProcess.getIdentifier()
        );
    }

    @ParameterizedTest
    @EnumSource(value = StatusSelectiveProcess.class)
    void when_requested_you_must_update_the_status_of_a_successful_selection_process(final StatusSelectiveProcess status) {
        when(this.jdbcTemplate.update(
                UPDATE_STATUS.sql,
                status.name(),
                this.selectiveProcess.getIdentifier()
        )).thenReturn(1);

        assertDoesNotThrow(() -> this.selectiveProcessRepository.updateStatus(this.selectiveProcess.getIdentifier(), status));

        verify(this.jdbcTemplate, only()).update(
                UPDATE_STATUS.sql,
                status.name(),
                this.selectiveProcess.getIdentifier()
        );
    }

    @ParameterizedTest
    @EnumSource(value = StatusSelectiveProcess.class)
    void when_requested_it_must_throw_a_NotFoundException_when_updating_the_status_of_a_selection_process(final StatusSelectiveProcess status) {
        when(this.jdbcTemplate.update(
                UPDATE_STATUS.sql,
                status.name(),
                this.selectiveProcess.getIdentifier()
        )).thenReturn(0);

        final NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> this.selectiveProcessRepository.updateStatus(this.selectiveProcess.getIdentifier(), status)
        );

        assertEquals(exception.getCode(), this.selectiveProcess.getIdentifier());
        assertEquals(exception.getClassType(), SelectiveProcess.class);

        verify(this.jdbcTemplate, only()).update(
                UPDATE_STATUS.sql,
                status.name(),
                this.selectiveProcess.getIdentifier()
        );
    }

}