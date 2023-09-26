package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusCandidacy;

import java.util.Objects;

public final class SummaryOfCandidacy {
    private final String identifier;
    private final StatusCandidacy status;
    private final String selectiveProcessTitle;

    public SummaryOfCandidacy(
            final String identifier,
            final StatusCandidacy status,
            final String selectiveProcessTitle
    ) {
        this.identifier = identifier;
        this.status = status;
        this.selectiveProcessTitle = selectiveProcessTitle;
    }

    public String getIdentifier() {
        return identifier;
    }

    public StatusCandidacy getStatus() {
        return status;
    }

    public String getSelectiveProcessTitle() {
        return selectiveProcessTitle;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SummaryOfCandidacy that = (SummaryOfCandidacy) object;
        return Objects.equals(identifier, that.identifier) && status == that.status && Objects.equals(selectiveProcessTitle, that.selectiveProcessTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, status, selectiveProcessTitle);
    }

}
