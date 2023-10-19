package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.selective.process.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.SelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto.PaginationQuery;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto.SelectiveProcessDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto.SelectiveProcessStepDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto.SelectiveProcessoPaginated;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.selective.process.request.SelectiveProcessRequest;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.selective.process.response.SelectiveProcessResponse;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.selective.process.response.SelectiveProcessoPaginatedResponse;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.selective.process.response.SpecificSelectiveProcessResponse;

import java.util.stream.Collectors;

public final class ConverterHelper {

    public static PaginationQuery toDto(final Integer pageSize, final Integer pageNumber) {
        return new PaginationQuery(pageSize, pageNumber);
    }

    public static SelectiveProcessoPaginatedResponse toResponse(final SelectiveProcessoPaginated result) {
        return new SelectiveProcessoPaginatedResponse(
                result.getSelectiveProcesses().stream().map(it -> new SelectiveProcessResponse(it.getIdentifier(), it.getTitle())).collect(Collectors.toList()),
                result.getPageNumber(),
                result.getPageSize(),
                result.getTotalPages(),
                result.getTotalElements(),
                result.getTotalResults()
        );
    }

    public static SpecificSelectiveProcessResponse toResponse(final SelectiveProcess result) {
        return new SpecificSelectiveProcessResponse(
                result.getIdentifier(),
                result.getTitle(),
                result.getDescription(),
                result.getDesiredPosition(),
                result.getStatus().name(),
                result.getResponsibilities(),
                result.getRequirements(),
                result.getAdditionalInfos()
        );
    }

    public static SelectiveProcessDto toDto(final SelectiveProcessRequest request) {
        return new SelectiveProcessDto(
                request.getTitle(),
                request.getDescription(),
                request.getDesiredPosition(),
                request.getResponsibilities(),
                request.getRequirements(),
                request.getAdditionalInfos(),
                request.getSteps().stream().map(it -> new SelectiveProcessStepDto(it.getIdentifier(), it.getLimitTime())).collect(Collectors.toList())
        );
    }

}
