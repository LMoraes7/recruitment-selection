package com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event;

import java.util.Objects;

public final class RegisterFeedback {
    private final String feedbackIdentifier;
    private final String candidacyIdentifier;
    private final String stepIdentifier;
    private final String result;

    public RegisterFeedback(
            final String feedbackIdentifier,
            final String candidacyIdentifier,
            final String stepIdentifier,
            final String result
    ) {
        this.feedbackIdentifier = feedbackIdentifier;
        this.candidacyIdentifier = candidacyIdentifier;
        this.stepIdentifier = stepIdentifier;
        this.result = result;
    }

    public String getFeedbackIdentifier() {
        return feedbackIdentifier;
    }

    public String getCandidacyIdentifier() {
        return candidacyIdentifier;
    }

    public String getStepIdentifier() {
        return stepIdentifier;
    }

    public String getResult() {
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        RegisterFeedback that = (RegisterFeedback) object;
        return Objects.equals(feedbackIdentifier, that.feedbackIdentifier) && Objects.equals(candidacyIdentifier, that.candidacyIdentifier) && Objects.equals(stepIdentifier, that.stepIdentifier) && Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackIdentifier, candidacyIdentifier, stepIdentifier, result);
    }

}
