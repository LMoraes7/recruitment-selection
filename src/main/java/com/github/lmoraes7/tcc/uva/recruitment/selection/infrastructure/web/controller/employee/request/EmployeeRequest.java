package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.employee.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Collections;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class EmployeeRequest {
    @Valid
    private PersonalDataRequest personalData;
    @NotNull
    @Size(min = 1)
    private Set<String> profilesIdentifiers;

    public PersonalDataRequest getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalDataRequest personalData) {
        this.personalData = personalData;
    }

    public Set<String> getProfilesIdentifiers() {
        return Collections.unmodifiableSet(profilesIdentifiers);
    }

    public void setProfilesIdentifiers(Set<String> profilesIdentifiers) {
        this.profilesIdentifiers = profilesIdentifiers;
    }

}
