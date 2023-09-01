package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidate.dto;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.common.dto.PersonalDataDto;

import java.util.Objects;

public final class CandidateDto {
    private final PersonalDataDto personalData;
    private final AccessCredentialsDto accessCredentials;

    public CandidateDto(final PersonalDataDto personalData, final AccessCredentialsDto accessCredentials) {
        this.personalData = personalData;
        this.accessCredentials = accessCredentials;
    }

    public PersonalDataDto getPersonalData() {
        return personalData;
    }

    public AccessCredentialsDto getAccessCredentials() {
        return accessCredentials;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CandidateDto that = (CandidateDto) object;
        return Objects.equals(personalData, that.personalData) && Objects.equals(accessCredentials, that.accessCredentials);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personalData, accessCredentials);
    }

}
