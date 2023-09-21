package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.AccessCredentials;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.PersonalData;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee.RegisterEmployeeService;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee.dto.EmployeeDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile.RegisterProfileService;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile.dto.ProfileDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question.dto.QuestionDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question.strategy.CreateQuestionStrategy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto.SelectiveProcessDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto.SelectiveProcessStepDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.StepDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.CreateStepStrategy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.function.FunctionRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.ProfileRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.selective.process.SelectiveProcessRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.StepRepository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.*;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_011;
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
            throw new BusinessException(APIX_002, invalidIdentifiers);

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
            throw new BusinessException(APIX_004, invalidIdentifiers);

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
            throw new BusinessException(APIX_010, invalidIdentifiers);

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
                throw new BusinessException(APIX_011, invalidExternalSteps);
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

}
