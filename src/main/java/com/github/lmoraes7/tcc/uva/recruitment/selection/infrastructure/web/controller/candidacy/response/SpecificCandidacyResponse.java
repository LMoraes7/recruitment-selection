package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.candidacy.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class SpecificCandidacyResponse {
    private String id;
    private String status;
    private SelectiveProcessSpecificCandidacyResponse selectiveProcess;
    private List<StepSpecificCandidacyResponse> steps;

    public SpecificCandidacyResponse(
            final String id,
            final String status,
            final SelectiveProcessSpecificCandidacyResponse selectiveProcess,
            final List<StepSpecificCandidacyResponse> steps
    ) {
        this.id = id;
        this.status = status;
        this.selectiveProcess = selectiveProcess;
        this.steps = steps;
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

    public SelectiveProcessSpecificCandidacyResponse getSelectiveProcess() {
        return selectiveProcess;
    }

    public void setSelectiveProcess(SelectiveProcessSpecificCandidacyResponse selectiveProcess) {
        this.selectiveProcess = selectiveProcess;
    }

    public List<StepSpecificCandidacyResponse> getSteps() {
        return steps;
    }

    public void setSteps(List<StepSpecificCandidacyResponse> steps) {
        this.steps = steps;
    }

}
