package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.SelectiveProcess;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public final class SelectiveProcessoPaginated {
    private final Collection<SelectiveProcess> selectiveProcesses;
    private final Integer pageNumber;
    private final Integer pageSize;
    private final Integer totalPages;
    private final Integer totalElements;
    private final Long totalResults;

    public SelectiveProcessoPaginated(
            final Collection<SelectiveProcess> selectiveProcesses,
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

    public Collection<SelectiveProcess> getSelectiveProcesses() {
        return Collections.unmodifiableCollection(selectiveProcesses);
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
        SelectiveProcessoPaginated that = (SelectiveProcessoPaginated) object;
        return Objects.equals(selectiveProcesses, that.selectiveProcesses) && Objects.equals(pageNumber, that.pageNumber) && Objects.equals(pageSize, that.pageSize) && Objects.equals(totalPages, that.totalPages) && Objects.equals(totalElements, that.totalElements) && Objects.equals(totalResults, that.totalResults);
    }

    @Override
    public int hashCode() {
        return Objects.hash(selectiveProcesses, pageNumber, pageSize, totalPages, totalElements, totalResults);
    }

}
