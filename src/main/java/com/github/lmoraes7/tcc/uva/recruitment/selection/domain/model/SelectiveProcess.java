package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusSelectiveProcess;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class SelectiveProcess {
    private String identifier;
    private String title;
    private String description;
    private String desiredPosition;
    private StatusSelectiveProcess status;
    private Set<String> responsibilities;
    private Set<String> requirements;
    private Set<String> additionalInfos;
    private List<StepSelectiveProcess> steps;

    public SelectiveProcess(final String identifier, final String title) {
        this.identifier = identifier;
        this.title = title;
    }

    public SelectiveProcess(
            final String identifier,
            final String title,
            final String description,
            final String desiredPosition,
            final StatusSelectiveProcess status,
            final Set<String> responsibilities,
            final Set<String> requirements,
            final Set<String> additionalInfos
    ) {
        this.identifier = identifier;
        this.title = title;
        this.description = description;
        this.desiredPosition = desiredPosition;
        this.status = status;
        this.responsibilities = responsibilities;
        this.requirements = requirements;
        this.additionalInfos = additionalInfos;
    }

    public SelectiveProcess(
            final String identifier,
            final String title,
            final String description,
            final String desiredPosition,
            final StatusSelectiveProcess status,
            final Set<String> responsibilities,
            final Set<String> requirements,
            final Set<String> additionalInfos,
            final List<StepSelectiveProcess> steps
    ) {
        this.identifier = identifier;
        this.title = title;
        this.description = description;
        this.desiredPosition = desiredPosition;
        this.status = status;
        this.responsibilities = responsibilities;
        this.requirements = requirements;
        this.additionalInfos = additionalInfos;
        this.steps = steps;
    }

    public String getIdentifier() {
        return identifier;
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

    public StatusSelectiveProcess getStatus() {
        return status;
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

    public List<StepSelectiveProcess> getSteps() {
        return Collections.unmodifiableList(steps);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SelectiveProcess that = (SelectiveProcess) object;
        return Objects.equals(identifier, that.identifier) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(desiredPosition, that.desiredPosition) && status == that.status && Objects.equals(responsibilities, that.responsibilities) && Objects.equals(requirements, that.requirements) && Objects.equals(additionalInfos, that.additionalInfos) && Objects.equals(steps, that.steps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, title, description, desiredPosition, status, responsibilities, requirements, additionalInfos, steps);
    }

}
