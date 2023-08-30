package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.employee.entity;

import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.employee.entity.vo.AccessCredentialsEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.employee.entity.vo.PersonalDataEntity;

public final class EmployeeEntity {
    private String identifier;
    private PersonalDataEntity personalData;
    private AccessCredentialsEntity accessCredentials;

    public EmployeeEntity(
            final String identifier,
            final PersonalDataEntity personalData,
            final AccessCredentialsEntity accessCredentials
    ) {
        this.identifier = identifier;
        this.personalData = personalData;
        this.accessCredentials = accessCredentials;
    }

    public String getIdentifier() {
        return identifier;
    }

    public PersonalDataEntity getPersonalData() {
        return personalData;
    }

    public AccessCredentialsEntity getAccessCredentials() {
        return accessCredentials;
    }

}
