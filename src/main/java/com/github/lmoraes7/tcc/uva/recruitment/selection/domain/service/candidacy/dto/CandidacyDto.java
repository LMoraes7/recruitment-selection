package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto;

import java.util.Objects;

public final class CandidacyDto {
    private final String selectiveProcessIdentifier;

    public CandidacyDto(final String selectiveProcessIdentifier) {
        this.selectiveProcessIdentifier = selectiveProcessIdentifier;
    }

    public String getSelectiveProcessIdentifier() {
        return selectiveProcessIdentifier;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CandidacyDto that = (CandidacyDto) object;
        return Objects.equals(selectiveProcessIdentifier, that.selectiveProcessIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(selectiveProcessIdentifier);
    }

}
