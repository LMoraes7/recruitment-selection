package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.selective.process.response;

import java.util.Set;

public final class SpecificSelectiveProcessResponse {
    private String id;
    private String title;
    private String description;
    private String desiredPosition;
    private String status;
    private Set<String> responsibilities;
    private Set<String> requirements;
    private Set<String> additionalInfos;

    public SpecificSelectiveProcessResponse(
            final String id,
            final String title,
            final String description,
            final String desiredPosition,
            final String status,
            final Set<String> responsibilities,
            final Set<String> requirements,
            final Set<String> additionalInfos
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.desiredPosition = desiredPosition;
        this.status = status;
        this.responsibilities = responsibilities;
        this.requirements = requirements;
        this.additionalInfos = additionalInfos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

}
