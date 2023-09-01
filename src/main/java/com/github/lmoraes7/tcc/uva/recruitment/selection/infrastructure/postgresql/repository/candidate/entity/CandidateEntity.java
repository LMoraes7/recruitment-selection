package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidate.entity;

import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.entity.vo.AccessCredentialsEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.commons.entity.vo.PersonalDataEntity;

import java.util.Objects;

public final class CandidateEntity {
    private final String identifier;
    private final PersonalDataEntity personalData;
    private final AccessCredentialsEntity accessCredentials;

    public CandidateEntity(
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CandidateEntity that = (CandidateEntity) object;
        return Objects.equals(identifier, that.identifier) && Objects.equals(personalData, that.personalData) && Objects.equals(accessCredentials, that.accessCredentials);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, personalData, accessCredentials);
    }

}
