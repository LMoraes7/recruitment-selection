package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.candidacy.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class StepSpecificCandidacyResponse {
    private String id;
    private String status;
    private String title;
    private String type;

    public StepSpecificCandidacyResponse(
            final String id,
            final String status,
            final String title,
            final String type
    ) {
        this.id = id;
        this.status = status;
        this.title = title;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
