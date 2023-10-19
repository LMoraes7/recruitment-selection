package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.candidacy.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Collection;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class CandidacyPaginatedResponse {
    private Collection<SummaryOfCandidacyResponse> candidacies;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPages;
    private Integer totalElements;
    private Long totalResults;

    public CandidacyPaginatedResponse(
            final Collection<SummaryOfCandidacyResponse> candidacies,
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

    public Collection<SummaryOfCandidacyResponse> getCandidacies() {
        return candidacies;
    }

    public void setCandidacies(Collection<SummaryOfCandidacyResponse> candidacies) {
        this.candidacies = candidacies;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

}
