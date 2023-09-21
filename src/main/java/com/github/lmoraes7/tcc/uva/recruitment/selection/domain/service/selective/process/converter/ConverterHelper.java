package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.SelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.StepSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.StatusSelectiveProcess;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.StepData;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto.SelectiveProcessDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.selective.process.dto.SelectiveProcessStepDto;

import java.util.List;
import java.util.stream.Collectors;

public final class ConverterHelper {

    public static SelectiveProcess toModel(final SelectiveProcessDto dto) {
        return new SelectiveProcess(
                GeneratorIdentifier.forSelectiveProcess(),
                dto.getTitle(),
                dto.getDescription(),
                dto.getDesiredPosition(),
                StatusSelectiveProcess.IN_PROGRESS,
                dto.getResponsibilities(),
                dto.getRequirements(),
                dto.getAdditionalInfos(),
                toModel(dto.getSteps())
        );
    }

    private static List<StepSelectiveProcess> toModel(final List<SelectiveProcessStepDto> dto) {
        return dto.stream().map(it -> new ExternalStep(new StepData(it.getIdentifier()), it.getLimitTime()))
                .collect(Collectors.toList());
    }

}
