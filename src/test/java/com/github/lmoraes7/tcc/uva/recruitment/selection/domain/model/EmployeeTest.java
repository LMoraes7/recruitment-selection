package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.StepData;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee.RegisterEmployeeService;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee.dto.EmployeeDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.feedback.dto.RegisterFeedbackDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile.RegisterProfileService;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile.dto.ProfileDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question.dto.QuestionDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question.strategy.CreateQuestionStrategy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto.SelectiveProcessDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto.SelectiveProcessStepDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.*;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.consult.answer.ConsultResponsesFromAnExecutedStepStrategy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.create.CreateStepStrategy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.CandidacyRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.feedback.FeedbackRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.function.FunctionRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.ProfileRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.CandidacyStepRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.SelectiveProcessRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.ExternalStepCandidacyRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.StepRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.*;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile.converter.ConverterHelper.toModel;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.utils.TestUtils.dummyObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class EmployeeTest {

    private final FunctionRepository functionRepository = mock(FunctionRepository.class);
    private final RegisterProfileService registerProfileService = mock(RegisterProfileService.class);
    private final ProfileRepository profileRepository = mock(ProfileRepository.class);
    private final RegisterEmployeeService registerEmployeeService = mock(RegisterEmployeeService.class);
    private final CreateQuestionStrategy questionStrategy = mock(CreateQuestionStrategy.class);
    private final CreateStepStrategy stepStrategy = mock(CreateStepStrategy.class);
    private final StepRepository stepRepository = mock(StepRepository.class);
    private final SelectiveProcessRepository selectiveProcessRepository = mock(SelectiveProcessRepository.class);
    private final CandidacyRepository candidacyRepository = mock(CandidacyRepository.class);
    private final ConsultResponsesFromAnExecutedStepStrategy consultResponsesFromAnExecutedStepStrategy = mock(ConsultResponsesFromAnExecutedStepStrategy.class);
    private final CandidacyStepRepository candidacyStepRepository = mock(CandidacyStepRepository.class);
    private final ExternalStepCandidacyRepository externalStepCandidacyRepository = mock(ExternalStepCandidacyRepository.class);
    private final FeedbackRepository feedbackRepository = mock(FeedbackRepository.class);

    private Employee employee;
    private ProfileDto profileDto;
    private EmployeeDto employeeDto;
    private QuestionDto questionDto;
    private StepDto stepDto;
    private SelectiveProcessDto selectiveProcessDto;
    private List<Step> steps;
    private SelectiveProcess selectiveProcess;
    private ConsultResponsesFromAnExecutedStepDto consultResponsesFromAnExecutedStepDto;
    private ResponsesFromAnExecutedStep responsesFromAnExecutedStep;
    private ReleaseStepForCandidateDto releaseStepForCandidateDto;
    private List<FindStepsDto> findStepsDtos;
    private Feedback feedback;
    private RegisterFeedbackDto registerFeedbackDto;

    @BeforeEach
    void setUp() {
        this.employee = dummyObject(Employee.class);
        this.profileDto = dummyObject(ProfileDto.class);
        this.employeeDto = dummyObject(EmployeeDto.class);
        this.questionDto = dummyObject(QuestionDto.class);
        this.stepDto = dummyObject(StepDto.class);
        this.selectiveProcessDto = new SelectiveProcessDto(
                dummyObject(String.class),
                dummyObject(String.class),
                dummyObject(String.class),
                Set.of(dummyObject(String.class)),
                Set.of(dummyObject(String.class)),
                Set.of(dummyObject(String.class)),
                List.of(
                        new SelectiveProcessStepDto(
                                GeneratorIdentifier.forStep(),
                                5767L
                        ),
                        new SelectiveProcessStepDto(
                                GeneratorIdentifier.forStep(),
                                345645L
                        ),
                        new SelectiveProcessStepDto(
                                GeneratorIdentifier.forStep(),
                                null
                        ),
                        new SelectiveProcessStepDto(
                                GeneratorIdentifier.forStep(),
                                65767L
                        )
                )
        );
        this.steps = this.selectiveProcessDto.getSteps().stream().map(it -> new ExternalStep(new StepData(it.getIdentifier(), TypeStep.UPLOAD_FILES))).collect(Collectors.toList());
        this.selectiveProcess = ConverterHelper.toModel(this.selectiveProcessDto);
        this.consultResponsesFromAnExecutedStepDto = dummyObject(ConsultResponsesFromAnExecutedStepDto.class);
        this.responsesFromAnExecutedStep = dummyObject(ResponsesFromAnExecutedStep.class);
        this.findStepsDtos = List.of(
            new FindStepsDto("1234567", "7654321", StatusStepCandidacy.COMPLETED, TypeStep.EXTERNAL),
            new FindStepsDto("7654321", null, StatusStepCandidacy.BLOCKED, TypeStep.EXTERNAL)
        );
        this.releaseStepForCandidateDto = new ReleaseStepForCandidateDto("7654321", GeneratorIdentifier.forCandidacy(), dummyObject(String.class), LocalDateTime.now());
        this.feedback = dummyObject(Feedback.class);
        this.registerFeedbackDto = dummyObject(RegisterFeedbackDto.class);
    }

    @Test
    void when_prompted_should_successfully_create_a_profile() {
        when(this.functionRepository.fetchIdentifiers(this.profileDto.getFunctionsIdentifiers()))
                .thenReturn(this.profileDto.getFunctionsIdentifiers());
        when(this.registerProfileService.save(profileDto)).thenReturn(toModel(this.profileDto));

        assertDoesNotThrow(() -> {
            final Profile profile = this.employee.createProfile(
                    this.profileDto,
                    this.functionRepository,
                    this.registerProfileService
            );

            assertNotNull(profile);
            assertNotNull(profile.getIdentifier());
            assertEquals(profile.getName(), this.profileDto.getName());
            assertEquals(
                    profile.getFunctions().stream().map(Function::getIdentifier).collect(Collectors.toSet()),
                    this.profileDto.getFunctionsIdentifiers()
            );
        });

        verify(this.functionRepository, only()).fetchIdentifiers(this.profileDto.getFunctionsIdentifiers());
        verify(this.registerProfileService, only()).save(profileDto);
    }

    @Test
    void when_requested_it_should_throw_a_BusinessException_when_an_invalid_Function_identifier_is_informed_when_create_profile() {
        final Collection<String> validIdentifiers = Collections.singleton(
                this.profileDto.getFunctionsIdentifiers().stream().findAny().get()
        );

        when(this.functionRepository.fetchIdentifiers(this.profileDto.getFunctionsIdentifiers()))
                .thenReturn(validIdentifiers);

        final BusinessException businessException = assertThrows(
                BusinessException.class,
                () -> this.employee.createProfile(
                        this.profileDto,
                        this.functionRepository,
                        this.registerProfileService
                )
        );

        final Set<String> invalidIdentifiers = this.profileDto.getFunctionsIdentifiers()
                .stream()
                .filter(it -> !validIdentifiers.contains(it))
                .collect(Collectors.toSet());

        assertNotNull(businessException.getError());
        assertEquals(businessException.getError(), APIX_002);
        assertNotNull(businessException.getArgs());
        assertEquals(businessException.getArgs().size(), invalidIdentifiers.size());
        assertTrue(businessException.getArgs().containsAll(invalidIdentifiers));

        verify(this.functionRepository, only()).fetchIdentifiers(this.profileDto.getFunctionsIdentifiers());
        verifyNoInteractions(this.registerProfileService);
    }

    @Test
    void when_prompted_should_successfully_create_a_employee() {
        when(this.profileRepository.fetchIdentifiers(this.employeeDto.getProfilesIdentifiers()))
                .thenReturn(this.employeeDto.getProfilesIdentifiers());
        when(this.registerEmployeeService.save(this.employeeDto)).thenReturn(this.employee);

        assertDoesNotThrow(() -> {
            this.employee.createEmployee(
                    this.employeeDto,
                    this.profileRepository,
                    this.registerEmployeeService
            );
        });

        verify(this.profileRepository, only()).fetchIdentifiers(this.employeeDto.getProfilesIdentifiers());
        verify(this.registerEmployeeService, only()).save(this.employeeDto);
    }

    @Test
    void when_requested_it_should_throw_a_BusinessException_when_an_invalid_Function_identifier_is_informed_when_create_employee() {
        final Collection<String> validIdentifiers = Collections.singleton(
                this.employeeDto.getProfilesIdentifiers().stream().findAny().get()
        );

        when(this.profileRepository.fetchIdentifiers(this.employeeDto.getProfilesIdentifiers()))
                .thenReturn(validIdentifiers);

        final BusinessException businessException = assertThrows(
                BusinessException.class,
                () -> this.employee.createEmployee(
                        this.employeeDto,
                        this.profileRepository,
                        this.registerEmployeeService
                )
        );

        final Set<String> invalidIdentifiers = this.employeeDto.getProfilesIdentifiers()
                .stream()
                .filter(it -> !validIdentifiers.contains(it))
                .collect(Collectors.toSet());

        assertNotNull(businessException.getError());
        assertEquals(businessException.getError(), APIX_004);
        assertNotNull(businessException.getArgs());
        assertEquals(businessException.getArgs().size(), invalidIdentifiers.size());
        assertTrue(businessException.getArgs().containsAll(invalidIdentifiers));

        verify(this.profileRepository, only()).fetchIdentifiers(this.employeeDto.getProfilesIdentifiers());
        verifyNoInteractions(this.registerEmployeeService);
    }

    @Test
    void when_prompted_to_create_a_question_it_should_run_successfully() {
        when(this.questionStrategy.execute(questionDto)).thenReturn(dummyObject(Question.class));

        assertDoesNotThrow(() -> this.employee.createQuestion(this.questionDto, this.questionStrategy));

        verify(this.questionStrategy, only()).execute(questionDto);
    }

    @Test
    void when_prompted_to_create_a_step_it_should_run_successfully() {
        when(this.stepStrategy.execute(this.stepDto)).thenReturn(dummyObject(ExternalStep.class));

        assertDoesNotThrow(() -> this.employee.createStep(this.stepDto, this.stepStrategy));

        verify(this.stepStrategy, only()).execute(this.stepDto);
    }

    @Test
    void when_requested_you_must_save_a_successful_selection_process() {
        final List<String> stepsIdentifiersToValidate = this.selectiveProcessDto.getSteps()
                .stream()
                .map(SelectiveProcessStepDto::getIdentifier)
                .toList();

        when(this.stepRepository.fetchSteps(stepsIdentifiersToValidate)).thenReturn(this.steps);
        when(this.selectiveProcessRepository.save(any(SelectiveProcess.class))).thenReturn(this.selectiveProcess);

        assertDoesNotThrow(() -> this.employee.createSelectiveProcess(this.stepRepository, this.selectiveProcessRepository, this.selectiveProcessDto));

        verify(this.stepRepository, only()).fetchSteps(stepsIdentifiersToValidate);
        verify(this.selectiveProcessRepository, only()).save(any(SelectiveProcess.class));
    }

    @Test
    void when_requested_you_must_save_a_successful_selection_process_2() {
        this.steps.set(2, new ExternalStep(new StepData(this.steps.get(2).getData().getIdentifier(), TypeStep.EXTERNAL)));

        final List<String> stepsIdentifiersToValidate = this.selectiveProcessDto.getSteps()
                .stream()
                .map(SelectiveProcessStepDto::getIdentifier)
                .toList();

        when(this.stepRepository.fetchSteps(stepsIdentifiersToValidate)).thenReturn(this.steps);
        when(this.selectiveProcessRepository.save(any(SelectiveProcess.class))).thenReturn(this.selectiveProcess);

        assertDoesNotThrow(() -> this.employee.createSelectiveProcess(this.stepRepository, this.selectiveProcessRepository, this.selectiveProcessDto));

        verify(this.stepRepository, only()).fetchSteps(stepsIdentifiersToValidate);
        verify(this.selectiveProcessRepository, only()).save(any(SelectiveProcess.class));
    }

    @Test
    void when_requested_it_must_throw_a_BusinessException_when_saving_a_selective_process_with_invalid_step_identifiers() {
        final List<String> stepsIdentifiersToValidate = this.selectiveProcessDto.getSteps()
                .stream()
                .map(SelectiveProcessStepDto::getIdentifier)
                .toList();
        this.steps.remove(0);

        when(this.stepRepository.fetchSteps(stepsIdentifiersToValidate)).thenReturn(this.steps);

        final BusinessException exception = assertThrows(
                BusinessException.class,
                () -> this.employee.createSelectiveProcess(this.stepRepository, this.selectiveProcessRepository, this.selectiveProcessDto)
        );

        assertNotNull(exception.getError());
        assertEquals(exception.getError(), APIX_010);

        verify(this.stepRepository, only()).fetchSteps(stepsIdentifiersToValidate);
        verifyNoInteractions(this.selectiveProcessRepository);
    }

    @Test
    void when_requested_it_must_throw_a_BusinessException_when_saving_a_selection_process_with_an_external_step_passing_a_time_limit() {
        this.steps.set(0, new ExternalStep(new StepData(this.steps.get(0).getData().getIdentifier(), TypeStep.EXTERNAL)));

        final List<String> stepsIdentifiersToValidate = this.selectiveProcessDto.getSteps()
                .stream()
                .map(SelectiveProcessStepDto::getIdentifier)
                .toList();

        when(this.stepRepository.fetchSteps(stepsIdentifiersToValidate)).thenReturn(this.steps);

        final BusinessException exception = assertThrows(
                BusinessException.class,
                () -> this.employee.createSelectiveProcess(this.stepRepository, this.selectiveProcessRepository, this.selectiveProcessDto)
        );

        assertNotNull(exception.getError());
        assertEquals(exception.getError(), APIX_011);

        verify(this.stepRepository, only()).fetchSteps(stepsIdentifiersToValidate);
        verifyNoInteractions(this.selectiveProcessRepository);
    }

    @Test
    void when_requested_you_must_successfully_complete_a_selection_process() {
        assertDoesNotThrow(() -> this.employee.closeSelectiveProcess(this.selectiveProcessRepository, this.candidacyRepository, this.selectiveProcess.getIdentifier()));

        verify(this.selectiveProcessRepository, only()).updateStatus(this.selectiveProcess.getIdentifier(), StatusSelectiveProcess.CLOSED);
        verify(this.candidacyRepository, only()).closeCandidacyBySelectiveProcess(this.selectiveProcess.getIdentifier());
    }

    @Test
    void when_requested_you_must_query_the_questions_successfully() {
        when(this.consultResponsesFromAnExecutedStepStrategy.execute(this.consultResponsesFromAnExecutedStepDto))
                .thenReturn(this.responsesFromAnExecutedStep);

        assertDoesNotThrow(() -> this.employee.consultResponseFromStep(this.consultResponsesFromAnExecutedStepStrategy, this.consultResponsesFromAnExecutedStepDto));

        verify(this.consultResponsesFromAnExecutedStepStrategy, only())
                .execute(this.consultResponsesFromAnExecutedStepDto);
    }

    @Test
    void when_requested_it_must_release_the_step_successfully() {
        when(this.candidacyStepRepository.getSteps(
            this.releaseStepForCandidateDto.getCandidacyIdentifier(),
            this.releaseStepForCandidateDto.getStepIdentifier()
        )).thenReturn(this.findStepsDtos);

        assertDoesNotThrow(() -> this.employee.releaseStepForCandidate(
                this.candidacyStepRepository,
                this.externalStepCandidacyRepository,
                this.releaseStepForCandidateDto
        ));

        verify(this.candidacyStepRepository, times(1)).getSteps(
                this.releaseStepForCandidateDto.getCandidacyIdentifier(),
                this.releaseStepForCandidateDto.getStepIdentifier()
        );
        verify(this.candidacyStepRepository, times(1)).updateStatus(
                this.releaseStepForCandidateDto.getCandidacyIdentifier(),
                this.releaseStepForCandidateDto.getStepIdentifier(),
                StatusStepCandidacy.WAITING_FOR_EXECUTION
        );
        verify(this.externalStepCandidacyRepository, only()).updateData(
                this.releaseStepForCandidateDto.getCandidacyIdentifier(),
                this.releaseStepForCandidateDto.getStepIdentifier(),
                this.releaseStepForCandidateDto.getLink(),
                this.releaseStepForCandidateDto.getDateTime()
        );
        verifyNoMoreInteractions(this.candidacyStepRepository);
    }

    @ParameterizedTest
    @EnumSource(value = TypeStep.class, mode = EnumSource.Mode.EXCLUDE, names = {"EXTERNAL"})
    void when_requested_it_must_release_the_step_successfully_2(final TypeStep typeStep) {
        this.findStepsDtos = List.of(
                new FindStepsDto("1234567", "7654321", StatusStepCandidacy.COMPLETED, TypeStep.EXTERNAL),
                new FindStepsDto("7654321", null, StatusStepCandidacy.BLOCKED, typeStep)
        );

        when(this.candidacyStepRepository.getSteps(
                this.releaseStepForCandidateDto.getCandidacyIdentifier(),
                this.releaseStepForCandidateDto.getStepIdentifier()
        )).thenReturn(this.findStepsDtos);

        assertDoesNotThrow(() -> this.employee.releaseStepForCandidate(
                this.candidacyStepRepository,
                this.externalStepCandidacyRepository,
                this.releaseStepForCandidateDto
        ));

        verify(this.candidacyStepRepository, times(1)).getSteps(
                this.releaseStepForCandidateDto.getCandidacyIdentifier(),
                this.releaseStepForCandidateDto.getStepIdentifier()
        );
        verify(this.candidacyStepRepository, times(1)).updateStatus(
                this.releaseStepForCandidateDto.getCandidacyIdentifier(),
                this.releaseStepForCandidateDto.getStepIdentifier(),
                StatusStepCandidacy.WAITING_FOR_EXECUTION
        );
        verifyNoMoreInteractions(this.candidacyStepRepository);
        verifyNoInteractions(this.externalStepCandidacyRepository);
    }

    @Test
    void when_requested_it_should_throw_a_NotFoundException() {
        when(this.candidacyStepRepository.getSteps(
                this.releaseStepForCandidateDto.getCandidacyIdentifier(),
                this.releaseStepForCandidateDto.getStepIdentifier()
        )).thenReturn(List.of());

        final NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> this.employee.releaseStepForCandidate(
                        this.candidacyStepRepository,
                        this.externalStepCandidacyRepository,
                        this.releaseStepForCandidateDto
                )
        );

        assertEquals(exception.getCode(), this.releaseStepForCandidateDto.getStepIdentifier());
        assertEquals(exception.getClassType(), StepCandidacy.class);

        verify(this.candidacyStepRepository, only()).getSteps(
                this.releaseStepForCandidateDto.getCandidacyIdentifier(),
                this.releaseStepForCandidateDto.getStepIdentifier()
        );
        verifyNoInteractions(this.externalStepCandidacyRepository);
    }

    @ParameterizedTest
    @EnumSource(value = StatusStepCandidacy.class, mode = EnumSource.Mode.EXCLUDE, names = {"COMPLETED"})
    void when_requested_it_should_throw_a_BusinessException_per_APIX_018(final StatusStepCandidacy status) {
        this.findStepsDtos = List.of(
                new FindStepsDto("1234567", "7654321", status, TypeStep.EXTERNAL),
                new FindStepsDto("7654321", null, StatusStepCandidacy.BLOCKED, TypeStep.EXTERNAL)
        );

        when(this.candidacyStepRepository.getSteps(
                this.releaseStepForCandidateDto.getCandidacyIdentifier(),
                this.releaseStepForCandidateDto.getStepIdentifier()
        )).thenReturn(this.findStepsDtos);

        final BusinessException exception = assertThrows(
                BusinessException.class,
                () -> this.employee.releaseStepForCandidate(
                        this.candidacyStepRepository,
                        this.externalStepCandidacyRepository,
                        this.releaseStepForCandidateDto
                )
        );

        assertEquals(exception.getError(), APIX_018);

        verify(this.candidacyStepRepository, only()).getSteps(
                this.releaseStepForCandidateDto.getCandidacyIdentifier(),
                this.releaseStepForCandidateDto.getStepIdentifier()
        );
        verifyNoInteractions(this.externalStepCandidacyRepository);
    }

    @Test
    void when_requested_it_should_throw_a_BusinessException_per_APIX_019() {
        this.releaseStepForCandidateDto = new ReleaseStepForCandidateDto("7654321", GeneratorIdentifier.forCandidacy(), null, LocalDateTime.now());

        this.findStepsDtos = List.of(
                new FindStepsDto("1234567", "7654321", StatusStepCandidacy.COMPLETED, TypeStep.EXTERNAL),
                new FindStepsDto("7654321", null, StatusStepCandidacy.BLOCKED, TypeStep.EXTERNAL)
        );

        when(this.candidacyStepRepository.getSteps(
                this.releaseStepForCandidateDto.getCandidacyIdentifier(),
                this.releaseStepForCandidateDto.getStepIdentifier()
        )).thenReturn(this.findStepsDtos);

        final BusinessException exception = assertThrows(
                BusinessException.class,
                () -> this.employee.releaseStepForCandidate(
                        this.candidacyStepRepository,
                        this.externalStepCandidacyRepository,
                        this.releaseStepForCandidateDto
                )
        );

        assertEquals(exception.getError(), APIX_019);

        verify(this.candidacyStepRepository, only()).getSteps(
                this.releaseStepForCandidateDto.getCandidacyIdentifier(),
                this.releaseStepForCandidateDto.getStepIdentifier()
        );
        verifyNoInteractions(this.externalStepCandidacyRepository);
    }

    @Test
    void when_requested_it_should_throw_a_BusinessException_per_APIX_019_2() {
        this.releaseStepForCandidateDto = new ReleaseStepForCandidateDto("7654321", GeneratorIdentifier.forCandidacy(), dummyObject(String.class), null);

        this.findStepsDtos = List.of(
                new FindStepsDto("1234567", "7654321", StatusStepCandidacy.COMPLETED, TypeStep.EXTERNAL),
                new FindStepsDto("7654321", null, StatusStepCandidacy.BLOCKED, TypeStep.EXTERNAL)
        );

        when(this.candidacyStepRepository.getSteps(
                this.releaseStepForCandidateDto.getCandidacyIdentifier(),
                this.releaseStepForCandidateDto.getStepIdentifier()
        )).thenReturn(this.findStepsDtos);

        final BusinessException exception = assertThrows(
                BusinessException.class,
                () -> this.employee.releaseStepForCandidate(
                        this.candidacyStepRepository,
                        this.externalStepCandidacyRepository,
                        this.releaseStepForCandidateDto
                )
        );

        assertEquals(exception.getError(), APIX_019);

        verify(this.candidacyStepRepository, only()).getSteps(
                this.releaseStepForCandidateDto.getCandidacyIdentifier(),
                this.releaseStepForCandidateDto.getStepIdentifier()
        );
        verifyNoInteractions(this.externalStepCandidacyRepository);
    }

    @Test
    void when_prompted_you_should_save_feedback_successfully() {
        when(this.candidacyStepRepository.getStep(
                this.registerFeedbackDto.getCandidacyIdentifier(),
                this.registerFeedbackDto.getStepIdentifier()
        )).thenReturn(Optional.of(new FindStepsDto("1234567", "7654321", StatusStepCandidacy.WAITING_FOR_EXECUTION, TypeStep.EXTERNAL)));
        when(this.feedbackRepository.save(
                any(),
                eq(this.registerFeedbackDto.getCandidacyIdentifier()),
                eq(this.registerFeedbackDto.getStepIdentifier())
        )).thenReturn(this.feedback);

        assertDoesNotThrow(() -> this.employee.registerFeedback(this.candidacyStepRepository, this.feedbackRepository, this.registerFeedbackDto));

        verify(this.candidacyStepRepository, times(1)).getStep(
                this.registerFeedbackDto.getCandidacyIdentifier(),
                this.registerFeedbackDto.getStepIdentifier()
        );
        verify(this.feedbackRepository, only()).save(
                any(),
                eq(this.registerFeedbackDto.getCandidacyIdentifier()),
                eq(this.registerFeedbackDto.getStepIdentifier())
        );
        verify(this.candidacyStepRepository, times(1)).updateStatus(
                this.registerFeedbackDto.getCandidacyIdentifier(),
                this.registerFeedbackDto.getStepIdentifier(),
                StatusStepCandidacy.COMPLETED
        );
        verifyNoMoreInteractions(this.candidacyStepRepository);
    }

    @ParameterizedTest
    @EnumSource(value = TypeStep.class, mode = EnumSource.Mode.EXCLUDE, names = {"EXTERNAL"})
    void when_prompted_you_should_save_feedback_successfully_2(final TypeStep typeStep) {
        when(this.candidacyStepRepository.getStep(
                this.registerFeedbackDto.getCandidacyIdentifier(),
                this.registerFeedbackDto.getStepIdentifier()
        )).thenReturn(Optional.of(new FindStepsDto("1234567", "7654321", StatusStepCandidacy.EXECUTED, typeStep)));
        when(this.feedbackRepository.save(
                any(),
                eq(this.registerFeedbackDto.getCandidacyIdentifier()),
                eq(this.registerFeedbackDto.getStepIdentifier())
        )).thenReturn(this.feedback);

        assertDoesNotThrow(() -> this.employee.registerFeedback(this.candidacyStepRepository, this.feedbackRepository, this.registerFeedbackDto));

        verify(this.candidacyStepRepository, times(1)).getStep(
                this.registerFeedbackDto.getCandidacyIdentifier(),
                this.registerFeedbackDto.getStepIdentifier()
        );
        verify(this.feedbackRepository, only()).save(
                any(),
                eq(this.registerFeedbackDto.getCandidacyIdentifier()),
                eq(this.registerFeedbackDto.getStepIdentifier())
        );
        verify(this.candidacyStepRepository, times(1)).updateStatus(
                this.registerFeedbackDto.getCandidacyIdentifier(),
                this.registerFeedbackDto.getStepIdentifier(),
                StatusStepCandidacy.COMPLETED
        );
        verifyNoMoreInteractions(this.candidacyStepRepository);
    }

    @Test
    void when_requested_it_should_throw_a_NotFoundException_() {
        when(this.candidacyStepRepository.getStep(
                this.registerFeedbackDto.getCandidacyIdentifier(),
                this.registerFeedbackDto.getStepIdentifier()
        )).thenReturn(Optional.empty());

        final NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> this.employee.registerFeedback(this.candidacyStepRepository, this.feedbackRepository, this.registerFeedbackDto)
        );

        assertEquals(exception.getCode(), this.registerFeedbackDto.getStepIdentifier());
        assertEquals(exception.getClassType(), StepCandidacy.class);

        verify(this.candidacyStepRepository, only()).getStep(
                this.registerFeedbackDto.getCandidacyIdentifier(),
                this.registerFeedbackDto.getStepIdentifier()
        );
        verifyNoInteractions(this.feedbackRepository);
    }

    @ParameterizedTest
    @EnumSource(value = StatusStepCandidacy.class, mode = EnumSource.Mode.EXCLUDE, names = {"WAITING_FOR_EXECUTION"})
    void when_requested_it_should_throw_a_BusinessException_per_APIX_020(final StatusStepCandidacy status) {
        when(this.candidacyStepRepository.getStep(
                this.registerFeedbackDto.getCandidacyIdentifier(),
                this.registerFeedbackDto.getStepIdentifier()
        )).thenReturn(Optional.of(new FindStepsDto("1234567", "7654321", status, TypeStep.EXTERNAL)));

        final BusinessException exception = assertThrows(
                BusinessException.class,
                () -> this.employee.registerFeedback(this.candidacyStepRepository, this.feedbackRepository, this.registerFeedbackDto)
        );

        assertEquals(exception.getError(), APIX_020);

        verify(this.candidacyStepRepository, only()).getStep(
                this.registerFeedbackDto.getCandidacyIdentifier(),
                this.registerFeedbackDto.getStepIdentifier()
        );
        verifyNoInteractions(this.feedbackRepository);
    }

    @ParameterizedTest
    @EnumSource(value = StatusStepCandidacy.class, mode = EnumSource.Mode.EXCLUDE, names = {"EXECUTED"})
    void when_requested_it_should_throw_a_BusinessException_per_APIX_022(final StatusStepCandidacy status) {
        when(this.candidacyStepRepository.getStep(
                this.registerFeedbackDto.getCandidacyIdentifier(),
                this.registerFeedbackDto.getStepIdentifier()
        )).thenReturn(Optional.of(new FindStepsDto("1234567", "7654321", status, TypeStep.THEORETICAL_TEST)));

        final BusinessException exception = assertThrows(
                BusinessException.class,
                () -> this.employee.registerFeedback(this.candidacyStepRepository, this.feedbackRepository, this.registerFeedbackDto)
        );

        assertEquals(exception.getError(), APIX_021);

        verify(this.candidacyStepRepository, only()).getStep(
                this.registerFeedbackDto.getCandidacyIdentifier(),
                this.registerFeedbackDto.getStepIdentifier()
        );
        verifyNoInteractions(this.feedbackRepository);
    }

    @ParameterizedTest
    @EnumSource(value = TypeStep.class, mode = EnumSource.Mode.EXCLUDE, names = {"EXTERNAL"})
    void when_requested_it_should_throw_a_BusinessException_per_APIX_022_2(final TypeStep typeStep) {
        when(this.candidacyStepRepository.getStep(
                this.registerFeedbackDto.getCandidacyIdentifier(),
                this.registerFeedbackDto.getStepIdentifier()
        )).thenReturn(Optional.of(new FindStepsDto("1234567", "7654321", StatusStepCandidacy.WAITING_FOR_EXECUTION, typeStep)));

        final BusinessException exception = assertThrows(
                BusinessException.class,
                () -> this.employee.registerFeedback(this.candidacyStepRepository, this.feedbackRepository, this.registerFeedbackDto)
        );

        assertEquals(exception.getError(), APIX_021);

        verify(this.candidacyStepRepository, only()).getStep(
                this.registerFeedbackDto.getCandidacyIdentifier(),
                this.registerFeedbackDto.getStepIdentifier()
        );
        verifyNoInteractions(this.feedbackRepository);
    }

}