package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class SelectiveProcessDto {
    private final String title;
    private final String description;
    private final String desiredPosition;
    private final Set<String> responsibilities;
    private final Set<String> requirements;
    private final Set<String> additionalInfos;
    private final List<SelectiveProcessStepDto> steps;

    public SelectiveProcessDto(
            final String title,
            final String description,
            final String desiredPosition,
            final Set<String> responsibilities,
            final Set<String> requirements,
            final Set<String> additionalInfos,
            final List<SelectiveProcessStepDto> steps
    ) {
        this.title = title;
        this.description = description;
        this.desiredPosition = desiredPosition;
        this.responsibilities = responsibilities;
        this.requirements = requirements;
        this.additionalInfos = additionalInfos;
        this.steps = steps;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDesiredPosition() {
        return desiredPosition;
    }

    public Set<String> getResponsibilities() {
        return Collections.unmodifiableSet(responsibilities);
    }

    public Set<String> getRequirements() {
        return Collections.unmodifiableSet(requirements);
    }

    public Set<String> getAdditionalInfos() {
        return Collections.unmodifiableSet(additionalInfos);
    }

    public List<SelectiveProcessStepDto> getSteps() {
        return Collections.unmodifiableList(steps);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SelectiveProcessDto that = (SelectiveProcessDto) object;
        return Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(desiredPosition, that.desiredPosition) && Objects.equals(responsibilities, that.responsibilities) && Objects.equals(requirements, that.requirements) && Objects.equals(additionalInfos, that.additionalInfos) && Objects.equals(steps, that.steps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, desiredPosition, responsibilities, requirements, additionalInfos, steps);
    }

}
