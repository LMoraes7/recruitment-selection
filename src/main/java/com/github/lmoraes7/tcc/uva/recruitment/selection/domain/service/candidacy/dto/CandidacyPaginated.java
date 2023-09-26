package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public final class CandidacyPaginated {
    private final Collection<SummaryOfCandidacy> candidacies;
    private final Integer pageNumber;
    private final Integer pageSize;
    private final Integer totalPages;
    private final Integer totalElements;
    private final Long totalResults;

    public CandidacyPaginated(
            final Collection<SummaryOfCandidacy> candidacies,
            final Integer pageNumber,
            final Integer pageSize,
            final Integer totalPages,
            final Integer totalElements,
            final Long totalResults
    ) {
        this.candidacies = candidacies;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.totalResults = totalResults;
    }

    public Collection<SummaryOfCandidacy> getCandidacies() {
        return Collections.unmodifiableCollection(candidacies);
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CandidacyPaginated that = (CandidacyPaginated) object;
        return Objects.equals(candidacies, that.candidacies) && Objects.equals(pageNumber, that.pageNumber) && Objects.equals(pageSize, that.pageSize) && Objects.equals(totalPages, that.totalPages) && Objects.equals(totalElements, that.totalElements) && Objects.equals(totalResults, that.totalResults);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidacies, pageNumber, pageSize, totalPages, totalElements, totalResults);
    }

}
