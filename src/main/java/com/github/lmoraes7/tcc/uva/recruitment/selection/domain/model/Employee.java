package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusStepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.AccessCredentials;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.PersonalData;
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

import java.util.*;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.*;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.utils.CommonFunctions.validateIdentifiers;

public final class Employee {
    private String identifier;
    private PersonalData personalData;
    private AccessCredentials accessCredentials;

    public Employee(
            final String identifier,
            final PersonalData personalData,
            final AccessCredentials accessCredentials
    ) {
        this.identifier = identifier;
        this.personalData = personalData;
        this.accessCredentials = accessCredentials;
    }

    public Employee(
            final String identifier,
            final AccessCredentials accessCredentials
    ) {
        this.identifier = identifier;
        this.accessCredentials = accessCredentials;
    }

    public String getIdentifier() {
        return identifier;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public AccessCredentials getAccessCredentials() {
        return accessCredentials;
    }

    public Profile createProfile(
            final ProfileDto dto,
            final FunctionRepository functionRepository,
            final RegisterProfileService registerProfileService
    ) {
        final Set<String> invalidIdentifiers = validateIdentifiers(
                functionRepository.fetchIdentifiers(dto.getFunctionsIdentifiers()),
                dto.getFunctionsIdentifiers()
        );

        if (!invalidIdentifiers.isEmpty())
            throw new BusinessException(APIX_002, "The identifiers provided are invalid: " + invalidIdentifiers);

        return registerProfileService.save(dto);
    }

    public Employee createEmployee(
            final EmployeeDto dto,
            final ProfileRepository profileRepository,
            final RegisterEmployeeService registerEmployeeService
    ) {
        final Set<String> invalidIdentifiers = validateIdentifiers(
                profileRepository.fetchIdentifiers(dto.getProfilesIdentifiers()),
                dto.getProfilesIdentifiers()
        );

        if (!invalidIdentifiers.isEmpty())
            throw new BusinessException(APIX_004, "The identifiers provided are invalid: " + invalidIdentifiers);

        return registerEmployeeService.save(dto);
    }

    public Question createQuestion(
            final QuestionDto dto,
            final CreateQuestionStrategy strategy
    ) {
        return strategy.execute(dto);
    }

    public Step createStep(
            final StepDto dto,
            final CreateStepStrategy strategy
    ) {
        return strategy.execute(dto);
    }

    public SelectiveProcess createSelectiveProcess(
            final StepRepository stepRepository,
            final SelectiveProcessRepository selectiveProcessRepository,
            final SelectiveProcessDto dto
    ) {
        final List<String> stepsIdentifiersToValidate = dto.getSteps()
                .stream()
                .map(SelectiveProcessStepDto::getIdentifier)
                .toList();
        final Collection<Step> validSteps = stepRepository.fetchSteps(stepsIdentifiersToValidate);

        final Set<String> invalidIdentifiers = validateIdentifiers(
                validSteps.stream().map(it -> it.getData().getIdentifier()).toList(),
                stepsIdentifiersToValidate
        );

        if (!invalidIdentifiers.isEmpty())
            throw new BusinessException(APIX_010, "The identifiers provided are invalid: " + invalidIdentifiers);

        final List<String> externalStepsIdentifiers = validSteps
                .stream()
                .filter(it -> it.getData().getType() == TypeStep.EXTERNAL)
                .map(it -> it.getData().getIdentifier())
                .toList();

        if (!externalStepsIdentifiers.isEmpty()) {
            final List<SelectiveProcessStepDto> invalidExternalSteps = dto.getSteps()
                    .stream()
                    .filter(it -> externalStepsIdentifiers.contains(it.getIdentifier()) && it.getLimitTime() != null)
                    .toList();

            if (!invalidExternalSteps.isEmpty())
                throw new BusinessException(APIX_011, "It is not possible to register a selection process because execution time limits were informed for external stages:  " + invalidIdentifiers);
        }

        return selectiveProcessRepository.save(ConverterHelper.toModel(dto));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Employee employee = (Employee) object;
        return Objects.equals(identifier, employee.identifier) && Objects.equals(personalData, employee.personalData) && Objects.equals(accessCredentials, employee.accessCredentials);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, personalData, accessCredentials);
    }

    public void closeSelectiveProcess(
            final SelectiveProcessRepository selectiveProcessRepository,
            final CandidacyRepository candidacyRepository,
            final String selectiveProcessIdentifier
    ) {
        selectiveProcessRepository.updateStatus(selectiveProcessIdentifier, StatusSelectiveProcess.CLOSED);
        candidacyRepository.closeCandidacyBySelectiveProcess(selectiveProcessIdentifier);
    }

    public ResponsesFromAnExecutedStep consultResponseFromStep(
            final ConsultResponsesFromAnExecutedStepStrategy strategy,
            final ConsultResponsesFromAnExecutedStepDto dto
    ) {
        return strategy.execute(dto);
    }

    public void releaseStepForCandidate(
            final CandidacyStepRepository candidacyStepRepository,
            final ExternalStepCandidacyRepository externalStepCandidacyRepository,
            final ReleaseStepForCandidateDto dto
    ) {
        final List<FindStepsDto> steps =
                candidacyStepRepository.getSteps(dto.getCandidacyIdentifier(), dto.getStepIdentifier());

        if (steps.isEmpty())
            throw new NotFoundException(dto.getStepIdentifier(), StepCandidacy.class);

        final Optional<FindStepsDto> optionalFirst = steps.stream()
                .filter(it -> Objects.equals(it.getNextStepIdentifier(), dto.getStepIdentifier()))
                .findFirst();

        if (optionalFirst.isPresent()) {
            final FindStepsDto firstStep = optionalFirst.get();
            if (firstStep.getStatusStepCandidacy() != StatusStepCandidacy.COMPLETED)
                throw new BusinessException(APIX_018, "The step cannot be released" + firstStep.getStepIdentifier());
        }

        final FindStepsDto step = steps.stream()
                .filter(it -> Objects.equals(it.getStepIdentifier(), dto.getStepIdentifier()))
                .findFirst().get();

        if (step.getTypeStep() != TypeStep.EXTERNAL) {
            candidacyStepRepository.updateStatus(
                    dto.getCandidacyIdentifier(),
                    dto.getStepIdentifier(),
                    StatusStepCandidacy.WAITING_FOR_EXECUTION
            );
            return;
        }

        if (dto.getLink() == null || dto.getDateTime() == null)
            throw new BusinessException(APIX_019, "The step cannot be released" + step.getStepIdentifier());

        candidacyStepRepository.updateStatus(
                dto.getCandidacyIdentifier(),
                dto.getStepIdentifier(),
                StatusStepCandidacy.WAITING_FOR_EXECUTION
        );
        externalStepCandidacyRepository.updateData(
                dto.getCandidacyIdentifier(),
                dto.getStepIdentifier(),
                dto.getLink(),
                dto.getDateTime()
        );
    }

    public Feedback registerFeedback(
            final CandidacyStepRepository candidacyStepRepository,
            final FeedbackRepository feedbackRepository,
            final RegisterFeedbackDto dto
    ) {
        final FindStepsDto step = candidacyStepRepository.getStep(dto.getCandidacyIdentifier(), dto.getStepIdentifier())
                .orElseThrow(() -> new NotFoundException(dto.getStepIdentifier(), StepCandidacy.class));

        if (step.getTypeStep() == TypeStep.EXTERNAL) {
            if (step.getStatusStepCandidacy() != StatusStepCandidacy.WAITING_FOR_EXECUTION)
                throw new BusinessException(APIX_020, "Feedback cannot be registered");
        } else if (step.getStatusStepCandidacy() != StatusStepCandidacy.EXECUTED)
            throw new BusinessException(APIX_021, "Feedback cannot be registered");

        final Feedback feedback = feedbackRepository.save(com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.feedback.converter.ConverterHelper.toModel(dto), dto.getCandidacyIdentifier(), dto.getStepIdentifier());

        candidacyStepRepository.updateStatus(
                dto.getCandidacyIdentifier(),
                dto.getStepIdentifier(),
                StatusStepCandidacy.COMPLETED
        );

        return feedback;
    }
}
