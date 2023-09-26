package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto;

import java.util.Objects;

public final class PaginationQuery {
    private final Integer pageSize;
    private final Integer pageNumber;

    public PaginationQuery(final Integer pageSize, final Integer pageNumber) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PaginationQuery that = (PaginationQuery) object;
        return Objects.equals(pageSize, that.pageSize) && Objects.equals(pageNumber, that.pageNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageSize, pageNumber);
    }

}
