package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.selective.process.response;

import java.util.Collection;

public final class SelectiveProcessoPaginatedResponse {
    private Collection<SelectiveProcessResponse> selectiveProcesses;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPages;
    private Integer totalElements;
    private Long totalResults;

    public SelectiveProcessoPaginatedResponse(
            final Collection<SelectiveProcessResponse> selectiveProcesses,
            final Integer pageNumber,
            final Integer pageSize,
            final Integer totalPages,
            final Integer totalElements,
            final Long totalResults
    ) {
        this.selectiveProcesses = selectiveProcesses;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.totalResults = totalResults;
    }

    public Collection<SelectiveProcessResponse> getSelectiveProcesses() {
        return selectiveProcesses;
    }

    public void setSelectiveProcesses(Collection<SelectiveProcessResponse> selectiveProcesses) {
        this.selectiveProcesses = selectiveProcesses;
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
