package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidacy.rowmapper.vo;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusCandidacy;

import java.util.Objects;

public final class CandidacyPageVo {
    private final String identifier;
    private final StatusCandidacy status;
    private final String selectiveProcessTitle;

    public CandidacyPageVo(
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
        CandidacyPageVo that = (CandidacyPageVo) object;
        return Objects.equals(identifier, that.identifier) && status == that.status && Objects.equals(selectiveProcessTitle, that.selectiveProcessTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, status, selectiveProcessTitle);
    }

}
