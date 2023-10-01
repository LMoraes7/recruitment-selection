package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.SelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.StepSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.StepData;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto.PaginationQuery;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto.SelectiveProcessoPaginated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.query.SelectiveProcessCommands.UPDATE_STATUS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.only;

@Tag("integration")
@SpringBootTest
final class SelectiveProcessRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SelectiveProcessRepository selectiveProcessRepository;

    private SelectiveProcess selectiveProcess;

    @BeforeEach
    void setUp() {
        this.selectiveProcess = new SelectiveProcess(
                GeneratorIdentifier.forSelectiveProcess(),
                "Title",
                "Description",
                "Desired Position",
                StatusSelectiveProcess.IN_PROGRESS,
                Set.of("responsibilities 1", "responsibilities 2", "responsibilities 3"),
                Set.of("requirements 1", "requirements 2", "requirements 3"),
                Set.of("additional Infos 1", "additional Infos 2", "additional Infos 3"),
                List.of(
                        new ExternalStep(
                                new StepData("STE-123456789"),
                                null
                        ),
                        new ExternalStep(
                                new StepData("STE-987654321"),
                                445675678L
                        ),
                        new ExternalStep(
                                new StepData("STE-564326567"),
                                8934756L
                        )
                )
        );
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/selective_process_repository_test.sql"})
    void when_prompted_it_should_save_successfully() {
        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "selection_processes"));

        assertDoesNotThrow(() -> this.selectiveProcessRepository.save(this.selectiveProcess));

        assertEquals(10, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "selection_processes"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/selective_process_repository_test.sql"})
    void when_requested_to_search_by_id_the_entity_must_be_returned_successfully() {
        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "selection_processes"));

        assertDoesNotThrow(() -> {
            final Optional<SelectiveProcess> optional = this.selectiveProcessRepository.findById("SEL-123456789");

            assertNotNull(optional);
            assertTrue(optional.isPresent());
        });

        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "selection_processes"));
    }

    @Test
    @Transactional
    void when_requested_to_search_by_id_it_should_not_return_the_entity() {
        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "selection_processes"));

        assertDoesNotThrow(() -> {
            final Optional<SelectiveProcess> optional = this.selectiveProcessRepository.findById("SEL-123456789");

            assertNotNull(optional);
            assertTrue(optional.isEmpty());
        });

        assertEquals(0, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "selection_processes"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/selective_process_repository_test.sql"})
    void when_requested_you_must_perform_a_paged_query_successfully() {
        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "selection_processes"));

        final PaginationQuery paginationQuery = new PaginationQuery(5, 0);

        assertDoesNotThrow(() -> {
            final SelectiveProcessoPaginated selectiveProcessoPaginated = this.selectiveProcessRepository.findAll(paginationQuery);

            assertEquals(selectiveProcessoPaginated.getSelectiveProcesses().size(), 5);
            assertEquals(selectiveProcessoPaginated.getPageNumber(), 0);
            assertEquals(selectiveProcessoPaginated.getPageSize(), 5);
            assertEquals(selectiveProcessoPaginated.getTotalPages(), 2);
            assertEquals(selectiveProcessoPaginated.getTotalElements(), 5);
            assertEquals(selectiveProcessoPaginated.getTotalResults(), 9);
        });

        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "selection_processes"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/selective_process_repository_test.sql"})
    void when_requested_you_must_perform_a_paged_query_successfully_2() {
        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "selection_processes"));

        final PaginationQuery paginationQuery = new PaginationQuery(5, 4);

        assertDoesNotThrow(() -> {
            final SelectiveProcessoPaginated selectiveProcessoPaginated = this.selectiveProcessRepository.findAll(paginationQuery);

            assertEquals(selectiveProcessoPaginated.getSelectiveProcesses().size(), 0);
            assertEquals(selectiveProcessoPaginated.getPageNumber(), 4);
            assertEquals(selectiveProcessoPaginated.getPageSize(), 5);
            assertEquals(selectiveProcessoPaginated.getTotalPages(), 2);
            assertEquals(selectiveProcessoPaginated.getTotalElements(), 0);
            assertEquals(selectiveProcessoPaginated.getTotalResults(), 9);
        });

        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "selection_processes"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/selective_process_repository_test.sql"})
    void when_requested_you_must_successfully_complete_the_selection_process_with_the_steps() {
        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "selection_processes"));

        assertDoesNotThrow(() -> {
            final Optional<SelectiveProcess> optional = this.selectiveProcessRepository.findWithStepsById("SEL-123456789");

            assertTrue(optional.isPresent());

            final List<StepSelectiveProcess> steps = optional.get().getSteps();

            assertNotNull(steps);
            assertEquals(7, steps.size());
            assertEquals("STE-987654321", steps.get(0).getData().getIdentifier());
            assertEquals("STE-564326562", steps.get(1).getData().getIdentifier());
            assertEquals("STE-564326563", steps.get(2).getData().getIdentifier());
            assertEquals("STE-987654324", steps.get(3).getData().getIdentifier());
            assertEquals("STE-564326567", steps.get(4).getData().getIdentifier());
            assertEquals("STE-123456788", steps.get(5).getData().getIdentifier());
            assertEquals("STE-123456789", steps.get(6).getData().getIdentifier());
        });

        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "selection_processes"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/selective_process_repository_test.sql"})
    void when_requested_must_fail_to_pursue_the_selection_process_with_the_steps() {
        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "selection_processes"));

        assertDoesNotThrow(() -> {
            final Optional<SelectiveProcess> optional = this.selectiveProcessRepository.findWithStepsById(this.selectiveProcess.getIdentifier());

            assertTrue(optional.isEmpty());
        });

        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "selection_processes"));
    }

    @ParameterizedTest
    @EnumSource(value = StatusSelectiveProcess.class)
    @Transactional
    @Sql(scripts = {"/script/selective_process_repository_test.sql"})
    void when_requested_you_must_update_the_status_of_a_successful_selection_process(final StatusSelectiveProcess status) {
        assertDoesNotThrow(() -> this.selectiveProcessRepository.updateStatus("SEL-123456789", status));
    }

    @ParameterizedTest
    @EnumSource(value = StatusSelectiveProcess.class)
    void when_requested_it_must_throw_a_NotFoundException_when_updating_the_status_of_a_selection_process(final StatusSelectiveProcess status) {
        assertThrows(
                NotFoundException.class,
                () -> this.selectiveProcessRepository.updateStatus(GeneratorIdentifier.forSelectiveProcess(), status)
        );
    }

}