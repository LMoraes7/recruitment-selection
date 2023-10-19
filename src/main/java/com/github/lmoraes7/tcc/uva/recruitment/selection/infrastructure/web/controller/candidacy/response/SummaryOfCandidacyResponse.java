package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.candidacy.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class SummaryOfCandidacyResponse {
    private String identifier;
    private String status;
    private String selectiveProcessTitle;

    public SummaryOfCandidacyResponse(
            final String identifier,
            final String status,
            final String selectiveProcessTitle
    ) {
        this.identifier = identifier;
        this.status = status;
        this.selectiveProcessTitle = selectiveProcessTitle;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSelectiveProcessTitle() {
        return selectiveProcessTitle;
    }

    public void setSelectiveProcessTitle(String selectiveProcessTitle) {
        this.selectiveProcessTitle = selectiveProcessTitle;
    }

}
