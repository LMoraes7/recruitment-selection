package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.AccessCredentials;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.PersonalData;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee.RegisterEmployeeService;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee.dto.EmployeeDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile.RegisterProfileService;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.profile.dto.ProfileDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question.dto.QuestionDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.question.strategy.CreateQuestionStrategy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.StepDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.CreateStepStrategy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.function.FunctionRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.ProfileRepository;

import java.util.Objects;
import java.util.Set;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_002;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_004;
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
