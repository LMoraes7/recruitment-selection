package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.candidacy.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.candidacy.dto.*;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.candidacy.response.*;

import java.util.stream.Collectors;

public final class ConverterHelper {

    public static CloseCandidacyDto toDto(
            final String candidacyIdentifier,
            final String selectiveProcessIdentifier
    ) {
        return new CloseCandidacyDto(candidacyIdentifier, selectiveProcessIdentifier);
    }

    public static PaginationQuery toDto(
            final Integer pageSize,
            final Integer pageNumber
    ) {
        return new PaginationQuery(pageSize, pageNumber);
    }

    public static CandidacyPaginatedResponse toResponse(final CandidacyPaginated result) {
        return new CandidacyPaginatedResponse(
                result.getCandidacies().stream().map(it -> new SummaryOfCandidacyResponse(it.getIdentifier(), it.getStatus().name(), it.getSelectiveProcessTitle())).collect(Collectors.toList()),
                result.getPageNumber(),
                result.getPageSize(),
                result.getTotalPages(),
                result.getTotalElements(),
                result.getTotalResults()
        );
    }

    public static SpecificCandidacyResponse toResponse(final SpecificCandidacyDto result) {
        return new SpecificCandidacyResponse(
                result.getId(),
                result.getStatus().name(),
                new SelectiveProcessSpecificCandidacyResponse(
                        result.getSelectiveProcess().getId(),
                        result.getSelectiveProcess().getTitle()
                ),
                result.getSteps().stream().map(it -> new StepSpecificCandidacyResponse(it.getId(), it.getStatus().name(), it.getTitle(), it.getType().name())).collect(Collectors.toList())
        );
    }

    public static CandidacyDto toDto(final String selectiveProcessIdentifier) {
        return new CandidacyDto(selectiveProcessIdentifier);
    }

}
