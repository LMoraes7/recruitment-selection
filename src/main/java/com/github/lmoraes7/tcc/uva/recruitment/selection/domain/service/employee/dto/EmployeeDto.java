package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.employee.dto;

import java.util.Collections;
import java.util.Set;

public final class EmployeeDto {
    private final PersonalDataDto personalData;
    private final Set<String> profilesIdentifiers;

    public EmployeeDto(final PersonalDataDto personalData, final Set<String> profilesIdentifiers) {
        this.personalData = personalData;
        this.profilesIdentifiers = profilesIdentifiers;
    }

    public PersonalDataDto getPersonalData() {
        return personalData;
    }

    public Set<String> getProfilesIdentifiers() {
        return Collections.unmodifiableSet(profilesIdentifiers);
    }

}
