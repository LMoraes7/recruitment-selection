package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.StepData;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.*;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.entity.CandidacyEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.rowmapper.CandidacyPageRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.rowmapper.CandidacyRowMapper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.rowmapper.vo.CandidacyPageVo;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.rowmapper.vo.CandidacyVo;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.CandidacyStepRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.conveter.ConverterHelper.toDto;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.conveter.ConverterHelper.toEntity;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.query.CandidacyCommands.*;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class CandidacyRepositoryTest {
    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private final CandidacyStepRepository candidacyStepRepository = mock(CandidacyStepRepository.class);
    private final CandidacyRowMapper candidacyRowMapper = mock(CandidacyRowMapper.class);
    private final CandidacyPageRowMapper candidacyPageRowMapper = mock(CandidacyPageRowMapper.class);
    private final CandidacyRepository candidacyRepository = new CandidacyRepository(
            this.jdbcTemplate,
            this.candidacyStepRepository,
            this.candidacyRowMapper,
            this.candidacyPageRowMapper
    );

    private String candidateIdentifier;
    private String selectiveProcessIdentifier;
    private Candidacy candidacy;
    private List<CandidacyVo> candidacyVos;
    private List<CandidacyPageVo> candidacyPageVos;
    private PaginationQuery paginationQuery;

    @BeforeEach
    void setUp() {
        this.candidateIdentifier = GeneratorIdentifier.forCandidate();
        this.selectiveProcessIdentifier = GeneratorIdentifier.forSelectiveProcess();
        this.candidacy = new Candidacy(
                GeneratorIdentifier.forCandidacy(),
                StatusCandidacy.IN_PROGRESS,
                List.of(
                        new ExternalStep(
                                new StepData(GeneratorIdentifier.forStep()),
                                547657L,
                                StatusStepCandidacy.WAITING_FOR_EXECUTION,
                                LocalDate.now()
                        ),
                        new ExternalStep(
                                new StepData(GeneratorIdentifier.forStep()),
                                5476765L,
                                StatusStepCandidacy.BLOCKED
                        ),
                        new ExternalStep(
                                new StepData(GeneratorIdentifier.forStep()),
                                null,
                                StatusStepCandidacy.BLOCKED
                        )
                )
        );
        this.candidacyVos = Arrays.asList(
                new CandidacyVo(
                        "APP-123456781",
                        dummyObject(StatusCandidacy.class),
                        "SEL-123456781",
                        dummyObject(String.class),
                        "STE-123456785",
                        "STE-123456786",
                        dummyObject(StatusStepCandidacy.class),
                        dummyObject(String.class),
                        dummyObject(TypeStep.class)
                ),
                new CandidacyVo(
                        "APP-123456781",
                        dummyObject(StatusCandidacy.class),
                        "SEL-123456781",
                        dummyObject(String.class),
                        "STE-123456782",
                        "STE-123456783",
                        dummyObject(StatusStepCandidacy.class),
                        dummyObject(String.class),
                        dummyObject(TypeStep.class)
                ),
                new CandidacyVo(
                        "APP-123456781",
                        dummyObject(StatusCandidacy.class),
                        "SEL-123456781",
                        dummyObject(String.class),
                        "STE-123456786",
                        null,
                        dummyObject(StatusStepCandidacy.class),
                        dummyObject(String.class),
                        dummyObject(TypeStep.class)
                ),
                new CandidacyVo(
                        "APP-123456781",
                        dummyObject(StatusCandidacy.class),
                        "SEL-123456781",
                        dummyObject(String.class),
                        "STE-123456781",
                        "STE-123456782",
                        dummyObject(StatusStepCandidacy.class),
                        dummyObject(String.class),
                        dummyObject(TypeStep.class)
                ),
                new CandidacyVo(
                        "APP-123456781",
                        dummyObject(StatusCandidacy.class),
                        "SEL-123456781",
                        dummyObject(String.class),
                        "STE-123456783",
                        "STE-123456784",
                        dummyObject(StatusStepCandidacy.class),
                        dummyObject(String.class),
                        dummyObject(TypeStep.class)
                ),
                new CandidacyVo(
                        "APP-123456781",
                        dummyObject(StatusCandidacy.class),
                        "SEL-123456781",
                        dummyObject(String.class),
                        "STE-123456784",
                        "STE-123456785",
                        dummyObject(StatusStepCandidacy.class),
                        dummyObject(String.class),
                        dummyObject(TypeStep.class)
                )
        );
        this.candidacyPageVos = List.of(
                dummyObject(CandidacyPageVo.class),
                dummyObject(CandidacyPageVo.class),
                dummyObject(CandidacyPageVo.class),
                dummyObject(CandidacyPageVo.class)
        );
        this.paginationQuery = new PaginationQuery(10, 20);
    }

    @Test
    void when_prompted_you_must_save_a_successful_application() {
        final CandidacyEntity entity = toEntity(
                this.candidateIdentifier,
                this.selectiveProcessIdentifier,
                this.candidacy
        );

        when(this.jdbcTemplate.update(
                SAVE.sql,
                entity.getIdentifier(),
                entity.getStatus(),
                entity.getCandidateIdentifier(),
                entity.getSelectiveProcessIdentifier()
        )).thenReturn(1);

        assertDoesNotThrow(
                () -> this.candidacyRepository.save(
                        this.candidateIdentifier,
                        this.selectiveProcessIdentifier,
                        this.candidacy
                )
        );

        verify(this.jdbcTemplate, only()).update(
                SAVE.sql,
                entity.getIdentifier(),
                entity.getStatus(),
                entity.getCandidateIdentifier(),
                entity.getSelectiveProcessIdentifier()
        );
        verify(this.candidacyStepRepository, only()).save(entity.getIdentifier(), entity.getSteps());
    }

    @Test
    void when_requested_you_must_consult_an_application_by_ID_successfully() {
        for (int i = 0; i < 10; i++) {
            Collections.shuffle(this.candidacyVos);

            when(this.jdbcTemplate.query(
                    FIND_BY_ID.sql,
                    this.candidacyRowMapper,
                    this.candidacy.getIdentifier(),
                    this.candidateIdentifier
            )).thenReturn(this.candidacyVos);

            assertDoesNotThrow(() -> {
                final Optional<SpecificCandidacyDto> optional = this.candidacyRepository.findById(
                        this.candidateIdentifier,
                        this.candidacy.getIdentifier()
                );

                assertNotNull(optional);
                assertTrue(optional.isPresent());

                final List<StepSpecificCandidacyDto> steps = optional.get().getSteps();

                assertNotNull(steps);
                assertEquals(this.candidacyVos.size(), steps.size());
                assertEquals("STE-123456781", steps.get(0).getId());
                assertEquals("STE-123456782", steps.get(1).getId());
                assertEquals("STE-123456783", steps.get(2).getId());
                assertEquals("STE-123456784", steps.get(3).getId());
                assertEquals("STE-123456785", steps.get(4).getId());
                assertEquals("STE-123456786", steps.get(5).getId());
            });
        }

        verify(this.jdbcTemplate, times(10)).query(
                FIND_BY_ID.sql,
                this.candidacyRowMapper,
                this.candidacy.getIdentifier(),
                this.candidateIdentifier
        );
        verifyNoMoreInteractions(this.jdbcTemplate);
    }

    @Test
    void when_prompted_should_fail_to_search_for_an_application_by_id() {
        when(this.jdbcTemplate.query(
                FIND_BY_ID.sql,
                this.candidacyRowMapper,
                this.candidacy.getIdentifier(),
                this.candidateIdentifier
        )).thenReturn(List.of());

        assertDoesNotThrow(() -> {
            final Optional<SpecificCandidacyDto> optional = this.candidacyRepository.findById(
                    this.candidateIdentifier,
                    this.candidacy.getIdentifier()
            );

            assertNotNull(optional);
            assertTrue(optional.isEmpty());
        });

        verify(this.jdbcTemplate, only()).query(
                FIND_BY_ID.sql,
                this.candidacyRowMapper,
                this.candidacy.getIdentifier(),
                this.candidateIdentifier
        );
    }

    @Test
    void when_requested_you_must_perform_a_paged_query_successfully() {
        final Pageable pageable = PageRequest.of(this.paginationQuery.getPageNumber(), this.paginationQuery.getPageSize());
        final PageImpl<SummaryOfCandidacy> page = new PageImpl<>(toDto(this.candidacyPageVos), pageable, 1);

        when(this.jdbcTemplate.query(
                FIND_ALL_BY_CANDIDATE_ID.sql,
                this.candidacyPageRowMapper,
                this.candidateIdentifier,
                pageable.getPageSize(),
                pageable.getOffset()
        )).thenReturn(this.candidacyPageVos);
        when(this.jdbcTemplate.queryForObject(COUNT_BY_CANDIDATE_ID.sql, Integer.class, this.candidateIdentifier)).thenReturn(1);

        assertDoesNotThrow(() -> {
            final CandidacyPaginated candidacyPaginated = this.candidacyRepository.findAll(this.candidateIdentifier, this.paginationQuery);

            assertEquals(candidacyPaginated.getCandidacies().size(), this.candidacyPageVos.size());
            assertEquals(candidacyPaginated.getPageNumber(), page.getNumber());
            assertEquals(candidacyPaginated.getPageSize(), page.getSize());
            assertEquals(candidacyPaginated.getTotalPages(), page.getTotalPages());
            assertEquals(candidacyPaginated.getTotalElements(), page.getNumberOfElements());
            assertEquals(candidacyPaginated.getTotalResults(), page.getTotalElements());
        });

        verify(this.jdbcTemplate, times(1)).query(
                FIND_ALL_BY_CANDIDATE_ID.sql,
                this.candidacyPageRowMapper,
                this.candidateIdentifier,
                pageable.getPageSize(),
                pageable.getOffset()
        );
        verify(this.jdbcTemplate, times(1)).queryForObject(COUNT_BY_CANDIDATE_ID.sql, Integer.class, this.candidateIdentifier);
        verifyNoMoreInteractions(this.jdbcTemplate);
    }

}