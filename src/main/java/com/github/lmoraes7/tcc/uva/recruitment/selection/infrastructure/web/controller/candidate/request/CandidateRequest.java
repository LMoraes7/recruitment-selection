package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.candidate.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class CandidateRequest {
    @NotNull
    @Valid
    private PersonalDataRequest personalData;
    @NotNull
    @Valid
    private AccessCredentialsRequest accessCredentials;

    public PersonalDataRequest getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalDataRequest personalData) {
        this.personalData = personalData;
    }

    public AccessCredentialsRequest getAccessCredentials() {
        return accessCredentials;
    }

    public void setAccessCredentials(AccessCredentialsRequest accessCredentials) {
        this.accessCredentials = accessCredentials;
    }

}
