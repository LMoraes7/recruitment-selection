package com.github.lmoraes7.tcc.uva.recruitment.selection.application.listener.event;

import java.util.Objects;

public final class NewRegisteredCandidacy {
    private final String id;
    private final String candidateId;
    private final String selectiveProcessId;

    public NewRegisteredCandidacy(
            final String id,
            final String candidateId,
            final String selectiveProcessId
    ) {
        this.id = id;
        this.candidateId = candidateId;
        this.selectiveProcessId = selectiveProcessId;
    }

    public String getId() {
        return id;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public String getSelectiveProcessId() {
        return selectiveProcessId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        NewRegisteredCandidacy that = (NewRegisteredCandidacy) object;
        return Objects.equals(id, that.id) && Objects.equals(candidateId, that.candidateId) && Objects.equals(selectiveProcessId, that.selectiveProcessId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, candidateId, selectiveProcessId);
    }

}
