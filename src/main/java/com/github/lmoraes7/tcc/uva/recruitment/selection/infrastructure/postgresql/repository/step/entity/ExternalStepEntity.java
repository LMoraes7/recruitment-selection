package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.entity;

public final class ExternalStepEntity extends StepEntity {
    public ExternalStepEntity(
            final String identifier,
            final String title,
            final String description,
            final String type
    ) {
        super(identifier, title, description, type);
    }

}
