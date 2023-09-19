package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Question;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.TheoricalTestStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.UploadFileStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.FileVo;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.StepData;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.StepDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.TypeUploadFileDto;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class ConverterHelper {

    public static ExternalStep toExternalStepModel(final StepDto dto) {
        return new ExternalStep(toData(dto));
    }

    public static UploadFileStep toUploadFileStepModel(final StepDto dto) {
        return new UploadFileStep(
                toData(dto),
                toModel(dto.getDataUploadFiles())
        );
    }

    public static TheoricalTestStep toTheoricalTestStepModel(final StepDto dto) {
        return new TheoricalTestStep(
                toData(dto),
                toModel(dto.getQuestionsIdentifiers())
        );
    }

    private static List<Question> toModel(final Set<String> questionsIdentifiers) {
        return questionsIdentifiers.stream().map(Question::new).collect(Collectors.toList());
    }

    private static Set<FileVo> toModel(final Collection<TypeUploadFileDto> dataUploadFiles) {
        return dataUploadFiles.stream().map(it -> new FileVo(it.getDescription(), it.getType())).collect(Collectors.toSet());
    }

    private static StepData toData(final StepDto dto) {
        return new StepData(
                GeneratorIdentifier.forStep(),
                dto.getTitle(),
                dto.getDescription(),
                dto.getType()
        );
    }

}
