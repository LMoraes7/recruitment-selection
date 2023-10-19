package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.selective.process.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class SelectiveProcessRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String desiredPosition;
    @NotNull
    @Size(min = 1, max = 10)
    private Set<String> responsibilities;
    @NotNull
    @Size(min = 1, max = 10)
    private Set<String> requirements;
    @Size(min = 1, max = 10)
    private Set<String> additionalInfos;
    @NotNull
    @Size(min = 2, max = 5)
    @Valid
    private List<SelectiveProcessStepRequest> steps;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDesiredPosition() {
        return desiredPosition;
    }

    public void setDesiredPosition(String desiredPosition) {
        this.desiredPosition = desiredPosition;
    }

    public Set<String> getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(Set<String> responsibilities) {
        this.responsibilities = responsibilities;
    }

    public Set<String> getRequirements() {
        return requirements;
    }

    public void setRequirements(Set<String> requirements) {
        this.requirements = requirements;
    }

    public Set<String> getAdditionalInfos() {
        return additionalInfos;
    }

    public void setAdditionalInfos(Set<String> additionalInfos) {
        this.additionalInfos = additionalInfos;
    }

    public List<SelectiveProcessStepRequest> getSteps() {
        return steps;
    }

    public void setSteps(List<SelectiveProcessStepRequest> steps) {
        this.steps = steps;
    }

}
