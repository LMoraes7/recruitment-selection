package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.SelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.StepData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "selection_processes"));

        assertDoesNotThrow(() -> this.selectiveProcessRepository.save(this.selectiveProcess));

        assertEquals(2, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "selection_processes"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/selective_process_repository_test.sql"})
    void when_requested_to_search_by_id_the_entity_must_be_returned_successfully() {
        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "selection_processes"));

        assertDoesNotThrow(() -> {
            final Optional<SelectiveProcess> optional = this.selectiveProcessRepository.findById("SEL-123456789");

            assertNotNull(optional);
            assertTrue(optional.isPresent());
        });

        assertEquals(1, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "selection_processes"));
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

}