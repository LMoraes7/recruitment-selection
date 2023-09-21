package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto;

import java.util.Objects;

public final class SelectiveProcessStepDto {
    private final String identifier;
    private final Long limitTime;

    public SelectiveProcessStepDto(
            final String identifier,
            final Long limitTime
    ) {
        this.identifier = identifier;
        this.limitTime = limitTime;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Long getLimitTime() {
        return limitTime;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SelectiveProcessStepDto that = (SelectiveProcessStepDto) object;
        return Objects.equals(identifier, that.identifier) && Objects.equals(limitTime, that.limitTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, limitTime);
    }

}
