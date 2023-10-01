package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.StepData;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.*;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ConsultSpecificStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ExecuteStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ExecuteStepCandidacyFindDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.SpecificExecutionStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.consult.ConsultSpecificExecutionStepCandidacyStrategy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.execute.ExecuteStepCandidacyStrategy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.execute.ExecuteTheoricalTestStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.CandidacyRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.SelectiveProcessRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.StepRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.*;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

final class CandidateTest {
    private final SelectiveProcessRepository selectiveProcessRepository = mock(SelectiveProcessRepository.class);
    private final CandidacyRepository candidacyRepository = mock(CandidacyRepository.class);
    private final ConsultSpecificExecutionStepCandidacyStrategy strategy = mock(ConsultSpecificExecutionStepCandidacyStrategy.class);
    private final StepRepository stepRepository = mock(StepRepository.class);
    private final ExecuteStepCandidacyStrategy executeStepCandidacyStrategy = mock(ExecuteTheoricalTestStepCandidacy.class);

    private Candidate candidate;
    private CandidacyDto candidacyDto;
    private SelectiveProcess selectiveProcess;
    private Candidacy candidacy;
    private SpecificCandidacyDto specificCandidacyDto;
    private PaginationQuery paginationQuery;
    private CandidacyPaginated candidacyPaginated;
    private CloseCandidacyDto closeCandidacyDto;
    private ConsultSpecificStepCandidacyDto consultSpecificStepCandidacyDto;
    private SpecificExecutionStepCandidacyDto specificExecutionStepCandidacyDto;
    private ExecuteStepCandidacyDto executeStepCandidacyDto;
    private ExecuteStepCandidacyFindDto executeStepCandidacyFind;

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
        this.closeCandidacyDto = dummyObject(CloseCandidacyDto.class);
        this.consultSpecificStepCandidacyDto = dummyObject(ConsultSpecificStepCandidacyDto.class);
        this.specificExecutionStepCandidacyDto = dummyObject(SpecificExecutionStepCandidacyDto.class);
        this.executeStepCandidacyDto = new ExecuteStepCandidacyDto(
                GeneratorIdentifier.forStep(),
                GeneratorIdentifier.forCandidacy(),
                GeneratorIdentifier.forSelectiveProcess(),
                TypeStep.THEORETICAL_TEST,
                null,
                null
        );
        this.executeStepCandidacyFind = new ExecuteStepCandidacyFindDto(
                GeneratorIdentifier.forStep(),
                StatusStepCandidacy.WAITING_FOR_EXECUTION,
                LocalDate.now().plusDays(3),
                LocalDate.now().minusDays(1)
        );
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

    @Test
    void when_prompted_you_must_successfully_close_an_application() {
        assertDoesNotThrow(() -> this.candidate.closeCandidacy(this.candidacyRepository, this.closeCandidacyDto));

        verify(this.candidacyRepository, only()).closeCandidacy(
                this.candidate.getIdentifier(),
                this.closeCandidacyDto.getSelectiveProcessIdentifier(),
                this.closeCandidacyDto.getCandidacyIdentifier()
        );
    }

    @Test
    void when_requested_must_consult_a_theoretical_step_successfully() {
        when(this.strategy.execute(this.candidate.getIdentifier(), this.consultSpecificStepCandidacyDto))
                .thenReturn(this.specificExecutionStepCandidacyDto);

        assertDoesNotThrow(() -> this.candidate.findSpecificStepCandidacy(this.strategy, this.consultSpecificStepCandidacyDto));

        verify(this.strategy, only()).execute(this.candidate.getIdentifier(), this.consultSpecificStepCandidacyDto);
    }

    @Test
    void when_prompted_must_perform_a_step_successfully() {
        when(this.stepRepository.find(
                this.executeStepCandidacyDto.getStepIdentifier(),
                this.executeStepCandidacyDto.getSelectiveProcessIdentifier(),
                this.executeStepCandidacyDto.getCandidacyIdentifier(),
                this.candidate.getIdentifier()
        )).thenReturn(Optional.of(this.executeStepCandidacyFind));

        assertDoesNotThrow(() -> this.candidate.executeStep(this.stepRepository, this.executeStepCandidacyStrategy, this.executeStepCandidacyDto));

        verify(this.stepRepository, times(1)).find(
                this.executeStepCandidacyDto.getStepIdentifier(),
                this.executeStepCandidacyDto.getSelectiveProcessIdentifier(),
                this.executeStepCandidacyDto.getCandidacyIdentifier(),
                this.candidate.getIdentifier()
        );
        verify(this.executeStepCandidacyStrategy, only()).execute(this.candidate.getIdentifier(), this.executeStepCandidacyDto);
        verify(this.stepRepository, times(1)).updateStatusStepCandidacy(
                this.executeStepCandidacyDto.getStepIdentifier(),
                this.executeStepCandidacyDto.getCandidacyIdentifier(),
                StatusStepCandidacy.EXECUTED
        );
        verifyNoMoreInteractions(this.stepRepository);
    }

    @Test
    void when_requested_it_must_launch_a_NotFound_when_it_does_not_find_the_step_to_be_executed() {
        when(this.stepRepository.find(
                this.executeStepCandidacyDto.getStepIdentifier(),
                this.executeStepCandidacyDto.getSelectiveProcessIdentifier(),
                this.executeStepCandidacyDto.getCandidacyIdentifier(),
                this.candidate.getIdentifier()
        )).thenReturn(Optional.empty());

        final NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> this.candidate.executeStep(this.stepRepository, this.executeStepCandidacyStrategy, this.executeStepCandidacyDto)
        );

        assertEquals(this.executeStepCandidacyDto.getStepIdentifier(), exception.getCode());
        assertEquals(StepCandidacy.class, exception.getClassType());

        verify(this.stepRepository, only()).find(
                this.executeStepCandidacyDto.getStepIdentifier(),
                this.executeStepCandidacyDto.getSelectiveProcessIdentifier(),
                this.executeStepCandidacyDto.getCandidacyIdentifier(),
                this.candidate.getIdentifier()
        );
        verifyNoInteractions(this.executeStepCandidacyStrategy);
    }

    @ParameterizedTest
    @EnumSource(value = StatusStepCandidacy.class, mode = EnumSource.Mode.EXCLUDE, names = {"WAITING_FOR_EXECUTION"})
    void when_requested_should_throw_a_BusinessException_when_the_step_is_in_invalid_status(final StatusStepCandidacy status) {
        this.executeStepCandidacyFind = new ExecuteStepCandidacyFindDto(
                GeneratorIdentifier.forStep(),
                status,
                LocalDate.now().plusDays(3),
                LocalDate.now().minusDays(1)
        );

        when(this.stepRepository.find(
                this.executeStepCandidacyDto.getStepIdentifier(),
                this.executeStepCandidacyDto.getSelectiveProcessIdentifier(),
                this.executeStepCandidacyDto.getCandidacyIdentifier(),
                this.candidate.getIdentifier()
        )).thenReturn(Optional.of(this.executeStepCandidacyFind));

        final BusinessException exception = assertThrows(
                BusinessException.class,
                () -> this.candidate.executeStep(this.stepRepository, this.executeStepCandidacyStrategy, this.executeStepCandidacyDto)
        );

        assertEquals(APIX_013, exception.getError());

        verify(this.stepRepository, only()).find(
                this.executeStepCandidacyDto.getStepIdentifier(),
                this.executeStepCandidacyDto.getSelectiveProcessIdentifier(),
                this.executeStepCandidacyDto.getCandidacyIdentifier(),
                this.candidate.getIdentifier()
        );
        verifyNoInteractions(this.executeStepCandidacyStrategy);
    }

    @Test
    void when_requested_it_should_throw_a_BusinessException_when_the_step_is_already_expired() {
        this.executeStepCandidacyFind = new ExecuteStepCandidacyFindDto(
                GeneratorIdentifier.forStep(),
                StatusStepCandidacy.WAITING_FOR_EXECUTION,
                LocalDate.now().minusDays(1),
                LocalDate.now().minusDays(1)
        );

        when(this.stepRepository.find(
                this.executeStepCandidacyDto.getStepIdentifier(),
                this.executeStepCandidacyDto.getSelectiveProcessIdentifier(),
                this.executeStepCandidacyDto.getCandidacyIdentifier(),
                this.candidate.getIdentifier()
        )).thenReturn(Optional.of(this.executeStepCandidacyFind));

        final BusinessException exception = assertThrows(
                BusinessException.class,
                () -> this.candidate.executeStep(this.stepRepository, this.executeStepCandidacyStrategy, this.executeStepCandidacyDto)
        );

        assertEquals(APIX_014, exception.getError());

        verify(this.stepRepository, only()).find(
                this.executeStepCandidacyDto.getStepIdentifier(),
                this.executeStepCandidacyDto.getSelectiveProcessIdentifier(),
                this.executeStepCandidacyDto.getCandidacyIdentifier(),
                this.candidate.getIdentifier()
        );
        verifyNoInteractions(this.executeStepCandidacyStrategy);
    }

}