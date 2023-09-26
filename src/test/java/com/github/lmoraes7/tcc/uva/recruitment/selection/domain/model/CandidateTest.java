package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.StepData;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.CandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.CandidacyPaginated;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.PaginationQuery;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.SpecificCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.CandidacyRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.SelectiveProcessRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_012;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

final class CandidateTest {
    private final SelectiveProcessRepository selectiveProcessRepository = mock(SelectiveProcessRepository.class);
    private final CandidacyRepository candidacyRepository = mock(CandidacyRepository.class);

    private Candidate candidate;
    private CandidacyDto candidacyDto;
    private SelectiveProcess selectiveProcess;
    private Candidacy candidacy;
    private SpecificCandidacyDto specificCandidacyDto;
    private PaginationQuery paginationQuery;
    private CandidacyPaginated candidacyPaginated;

    @BeforeEach
    void setUp() {
        this.candidate = dummyObject(Candidate.class);
        this.candidacyDto = dummyObject(CandidacyDto.class);
        this.selectiveProcess = new SelectiveProcess(
                GeneratorIdentifier.forSelectiveProcess(),
                StatusSelectiveProcess.IN_PROGRESS,
                List.of(
                        new ExternalStep(new StepData(GeneratorIdentifier.forStep(), TypeStep.UPLOAD_FILES), 4364564L),
                        new ExternalStep(new StepData(GeneratorIdentifier.forStep(), TypeStep.THEORETICAL_TEST), 4765547L),
                        new ExternalStep(new StepData(GeneratorIdentifier.forStep(), TypeStep.EXTERNAL)),
                        new ExternalStep(new StepData(GeneratorIdentifier.forStep(), TypeStep.UPLOAD_FILES), 576786L)
                )
        );
        this.candidacy = ConverterHelper.toModel(selectiveProcess);
        this.specificCandidacyDto = dummyObject(SpecificCandidacyDto.class);
        this.paginationQuery = new PaginationQuery(10, 20);
        this.candidacyPaginated = dummyObject(CandidacyPaginated.class);
    }

    @Test
    void when_prompted_you_must_save_a_successful_application() {
        when(this.selectiveProcessRepository.findWithStepsById(this.candidacyDto.getSelectiveProcessIdentifier()))
                .thenReturn(Optional.of(this.selectiveProcess));
        when(this.candidacyRepository.save(
                eq(this.candidate.getIdentifier()),
                eq(this.selectiveProcess.getIdentifier()),
                any(Candidacy.class)
        )).thenReturn(this.candidacy);

        assertDoesNotThrow(
                () -> this.candidate.performCandidacy(
                        this.selectiveProcessRepository,
                        this.candidacyRepository,
                        this.candidacyDto
                )
        );

        verify(this.selectiveProcessRepository, only()).findWithStepsById(this.candidacyDto.getSelectiveProcessIdentifier());
        verify(this.candidacyRepository, only()).save(
                eq(this.candidate.getIdentifier()),
                eq(this.selectiveProcess.getIdentifier()),
                any(Candidacy.class)
        );
    }

    @Test
    void when_requested_it_must_fail_to_save_an_application_for_a_non_existent_selection_process() {
        when(this.selectiveProcessRepository.findWithStepsById(this.candidacyDto.getSelectiveProcessIdentifier()))
                .thenReturn(Optional.empty());

        final NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> this.candidate.performCandidacy(
                        this.selectiveProcessRepository,
                        this.candidacyRepository,
                        this.candidacyDto
                )
        );

        assertEquals(exception.getCode(), this.candidacyDto.getSelectiveProcessIdentifier());
        assertEquals(exception.getClassType(), SelectiveProcess.class);

        verify(this.selectiveProcessRepository, only()).findWithStepsById(this.candidacyDto.getSelectiveProcessIdentifier());
        verifyNoInteractions(this.candidacyRepository);
    }

    @Test
    void when_requested_it_should_fail_to_save_an_application_for_a_selection_process_that_is_closed() {
        this.selectiveProcess = new SelectiveProcess(
                GeneratorIdentifier.forSelectiveProcess(),
                StatusSelectiveProcess.CLOSED,
                List.of(
                        new ExternalStep(new StepData(GeneratorIdentifier.forStep(), TypeStep.UPLOAD_FILES), 4364564L),
                        new ExternalStep(new StepData(GeneratorIdentifier.forStep(), TypeStep.THEORETICAL_TEST), 4765547L),
                        new ExternalStep(new StepData(GeneratorIdentifier.forStep(), TypeStep.EXTERNAL)),
                        new ExternalStep(new StepData(GeneratorIdentifier.forStep(), TypeStep.UPLOAD_FILES), 576786L)
                )
        );

        when(this.selectiveProcessRepository.findWithStepsById(this.candidacyDto.getSelectiveProcessIdentifier()))
                .thenReturn(Optional.of(this.selectiveProcess));

        final BusinessException exception = assertThrows(
                BusinessException.class,
                () -> this.candidate.performCandidacy(
                        this.selectiveProcessRepository,
                        this.candidacyRepository,
                        this.candidacyDto
                )
        );

        assertEquals(exception.getError(), APIX_012);

        verify(this.selectiveProcessRepository, only()).findWithStepsById(this.candidacyDto.getSelectiveProcessIdentifier());
        verifyNoInteractions(this.candidacyRepository);
    }

    @Test
    void when_requested_you_must_consult_an_application_by_ID_successfully() {
        when(this.candidacyRepository.findById(
                this.candidate.getIdentifier(),
                this.candidacy.getIdentifier()
        )).thenReturn(Optional.of(this.specificCandidacyDto));

        assertDoesNotThrow(() -> this.candidate.findSpecificCandidacy(this.candidacyRepository, this.candidacy.getIdentifier()));

        verify(this.candidacyRepository, only()).findById(
                this.candidate.getIdentifier(),
                this.candidacy.getIdentifier()
        );
    }

    @Test
    void when_prompted_should_fail_to_search_for_an_application_by_id() {
        when(this.candidacyRepository.findById(
                this.candidate.getIdentifier(),
                this.candidacy.getIdentifier()
        )).thenReturn(Optional.empty());

        final NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> this.candidate.findSpecificCandidacy(this.candidacyRepository, this.candidacy.getIdentifier())
        );

        assertEquals(exception.getCode(), this.candidacy.getIdentifier());
        assertEquals(exception.getClassType(), Candidacy.class);

        verify(this.candidacyRepository, only()).findById(
                this.candidate.getIdentifier(),
                this.candidacy.getIdentifier()
        );
    }

    @Test
    void when_requested_you_must_perform_a_paged_query_successfully() {
        when(this.candidacyRepository.findAll(this.candidate.getIdentifier(), this.paginationQuery))
                .thenReturn(this.candidacyPaginated);

        assertDoesNotThrow(() -> this.candidate.findCandidacies(this.candidacyRepository, this.paginationQuery));

        verify(this.candidacyRepository, only()).findAll(this.candidate.getIdentifier(), this.paginationQuery);
    }

}