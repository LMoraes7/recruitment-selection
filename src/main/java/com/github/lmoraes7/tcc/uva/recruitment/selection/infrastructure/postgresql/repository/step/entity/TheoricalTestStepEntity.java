package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.entity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class TheoricalTestStepEntity extends StepEntity {
    private List<String> questionIdentifiers;

    public TheoricalTestStepEntity(
            final String identifier,
            final String title,
            final String description,
            final String type,
            final List<String> questionIdentifiers
    ) {
        super(identifier, title, description, type);
        this.questionIdentifiers = questionIdentifiers;
    }

    public List<String> getQuestionIdentifiers() {
        return Collections.unmodifiableList(questionIdentifiers);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        TheoricalTestStepEntity that = (TheoricalTestStepEntity) object;
        return Objects.equals(questionIdentifiers, that.questionIdentifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), questionIdentifiers);
    }

}
