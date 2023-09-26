package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.StepData;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.CandidacyPaginated;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.PaginationQuery;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.SpecificCandidacyDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
@SpringBootTest
class CandidacyRepositoryItTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CandidacyRepository candidacyRepository;

    private String candidateIdentifier;
    private String selectiveProcessIdentifier;
    private String candidacyIdentifier;
    private Candidacy candidacy;

    @BeforeEach
    void setUp() {
        this.candidateIdentifier = "CAN-123456789";
        this.selectiveProcessIdentifier = "SEL-123456789";
        this.candidacyIdentifier = "APP-123456789";
        this.candidacy = new Candidacy(
                GeneratorIdentifier.forCandidacy(),
                StatusCandidacy.IN_PROGRESS,
                List.of(
                        new ExternalStep(
                                new StepData("STE-987654321"),
                                4564576L,
                                StatusStepCandidacy.WAITING_FOR_EXECUTION,
                                LocalDate.now()
                        ),
                        new ExternalStep(
                                new StepData("STE-564326562"),
                                798789L,
                                StatusStepCandidacy.BLOCKED
                        ),
                        new ExternalStep(
                                new StepData("STE-564326563"),
                                7876576L,
                                StatusStepCandidacy.BLOCKED
                        ),
                        new ExternalStep(
                                new StepData("STE-987654324"),
                                65768L,
                                StatusStepCandidacy.BLOCKED
                        ),
                        new ExternalStep(
                                new StepData("STE-564326567"),
                                7687L,
                                StatusStepCandidacy.BLOCKED
                        ),
                        new ExternalStep(
                                new StepData("STE-123456788"),
                                null,
                                StatusStepCandidacy.BLOCKED
                        ),
                        new ExternalStep(
                                new StepData("STE-123456789"),
                                null,
                                StatusStepCandidacy.BLOCKED
                        )
                )
        );
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/candidacy_repository_test.sql"})
    void when_prompted_you_must_save_a_successful_application() {
        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "applications"));

        assertDoesNotThrow(
                () -> this.candidacyRepository.save(
                        this.candidateIdentifier,
                        this.selectiveProcessIdentifier,
                        this.candidacy
                )
        );

        assertEquals(10, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "applications"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/candidacy_repository_test.sql"})
    void when_requested_you_must_consult_an_application_by_ID_successfully() {
        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "applications"));

        assertDoesNotThrow(() -> {
            final Optional<SpecificCandidacyDto> optional = this.candidacyRepository.findById(
                    this.candidateIdentifier,
                    this.candidacyIdentifier
            );

            assertNotNull(optional);
            assertTrue(optional.isPresent());
        });

        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "applications"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/candidacy_repository_test.sql"})
    void when_prompted_should_fail_to_search_for_an_application_by_id() {
        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "applications"));

        assertDoesNotThrow(() -> {
            final Optional<SpecificCandidacyDto> optional = this.candidacyRepository.findById(
                    GeneratorIdentifier.forCandidate(),
                    this.candidacyIdentifier
            );

            assertNotNull(optional);
            assertTrue(optional.isEmpty());
        });

        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "applications"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/candidacy_repository_test.sql"})
    void when_prompted_should_fail_to_search_for_an_application_by_id_2() {
        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "applications"));

        assertDoesNotThrow(() -> {
            final Optional<SpecificCandidacyDto> optional = this.candidacyRepository.findById(
                    this.candidateIdentifier,
                    GeneratorIdentifier.forCandidacy()
            );

            assertNotNull(optional);
            assertTrue(optional.isEmpty());
        });

        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "applications"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/candidacy_repository_test.sql"})
    void when_requested_you_must_perform_a_paged_query_successfully() {
        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "applications"));

        final PaginationQuery paginationQuery = new PaginationQuery(5, 0);

        assertDoesNotThrow(() -> {
            final CandidacyPaginated candidacyPaginated = this.candidacyRepository.findAll(this.candidateIdentifier, paginationQuery);

            assertEquals(candidacyPaginated.getCandidacies().size(), 5);
            assertEquals(candidacyPaginated.getPageNumber(), 0);
            assertEquals(candidacyPaginated.getPageSize(), 5);
            assertEquals(candidacyPaginated.getTotalPages(), 2);
            assertEquals(candidacyPaginated.getTotalElements(), 5);
            assertEquals(candidacyPaginated.getTotalResults(), 9);
        });

        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "applications"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/candidacy_repository_test.sql"})
    void when_requested_you_must_perform_a_paged_query_successfully_2() {
        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "applications"));

        final PaginationQuery paginationQuery = new PaginationQuery(5, 4);

        assertDoesNotThrow(() -> {
            final CandidacyPaginated candidacyPaginated = this.candidacyRepository.findAll(this.candidateIdentifier, paginationQuery);

            assertEquals(candidacyPaginated.getCandidacies().size(), 0);
            assertEquals(candidacyPaginated.getPageNumber(), 4);
            assertEquals(candidacyPaginated.getPageSize(), 5);
            assertEquals(candidacyPaginated.getTotalPages(), 2);
            assertEquals(candidacyPaginated.getTotalElements(), 0);
            assertEquals(candidacyPaginated.getTotalResults(), 9);
        });

        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "applications"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/candidacy_repository_test.sql"})
    void when_prompted_you_must_successfully_close_an_application() {
        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "applications"));

        assertDoesNotThrow(() -> this.candidacyRepository.closeCandidacy(this.candidateIdentifier, this.selectiveProcessIdentifier, this.candidacyIdentifier));

        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "applications"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/candidacy_repository_test.sql"})
    void when_requested_it_must_throw_a_NotFoundException_when_closing_a_non_existent_application() {
        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "applications"));

        final NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> this.candidacyRepository.closeCandidacy(GeneratorIdentifier.forCandidate(), this.selectiveProcessIdentifier, this.candidacyIdentifier)
        );

        assertEquals(exception.getCode(), this.candidacyIdentifier);
        assertEquals(exception.getClassType(), Candidacy.class);

        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "applications"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/candidacy_repository_test.sql"})
    void when_requested_it_must_throw_a_NotFoundException_when_closing_a_non_existent_application_2() {
        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "applications"));

        final NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> this.candidacyRepository.closeCandidacy(this.candidateIdentifier, GeneratorIdentifier.forSelectiveProcess(), this.candidacyIdentifier)
        );

        assertEquals(exception.getCode(), this.candidacyIdentifier);
        assertEquals(exception.getClassType(), Candidacy.class);

        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "applications"));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/script/candidacy_repository_test.sql"})
    void when_requested_it_must_throw_a_NotFoundException_when_closing_a_non_existent_application_3() {
        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "applications"));

        final NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> this.candidacyRepository.closeCandidacy(this.candidateIdentifier, this.selectiveProcessIdentifier, this.candidacy.getIdentifier())
        );

        assertEquals(exception.getCode(), this.candidacy.getIdentifier());
        assertEquals(exception.getClassType(), Candidacy.class);

        assertEquals(9, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "applications"));
    }

}