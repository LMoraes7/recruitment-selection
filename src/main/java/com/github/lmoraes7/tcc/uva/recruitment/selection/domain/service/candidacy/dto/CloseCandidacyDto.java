package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto;

import java.util.Objects;

public final class CloseCandidacyDto {
    private final String candidacyIdentifier;
    private final String selectiveProcessIdentifier;

    public CloseCandidacyDto(
            final String candidacyIdentifier,
            final String selectiveProcessIdentifier
    ) {
        this.candidacyIdentifier = candidacyIdentifier;
        this.selectiveProcessIdentifier = selectiveProcessIdentifier;
    }

    public String getCandidacyIdentifier() {
        return candidacyIdentifier;
    }

    public String getSelectiveProcessIdentifier() {
        return selectiveProcessIdentifier;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CloseCandidacyDto that = (CloseCandidacyDto) object;
        return Objects.equals(candidacyIdentifier, that.candidacyIdentifier) && Objects.equals(selectiveProcessIdentifier, that.selectiveProcessIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidacyIdentifier, selectiveProcessIdentifier);
    }

}
