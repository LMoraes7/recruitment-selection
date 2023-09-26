package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto;

import java.util.Objects;

public final class SelectiveProcessSpecificCandidacyDto {
    private final String id;
    private final String title;

    public SelectiveProcessSpecificCandidacyDto(final String id, final String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SelectiveProcessSpecificCandidacyDto that = (SelectiveProcessSpecificCandidacyDto) object;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

}
