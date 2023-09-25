package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.StepData;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.CandidacyDto;
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

}