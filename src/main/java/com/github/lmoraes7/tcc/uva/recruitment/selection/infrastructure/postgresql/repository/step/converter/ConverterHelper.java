package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Question;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.TheoricalTestStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.UploadFileStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.FileVo;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.entity.ExternalStepEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.entity.TheoricalTestStepEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.entity.UploadFileStepEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.entity.vo.FileVoEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class ConverterHelper {

    public static ExternalStepEntity toEntity(final ExternalStep step) {
        return new ExternalStepEntity(
                step.getData().getIdentifier(),
                step.getData().getTitle(),
                step.getData().getDescription(),
                step.getData().getType().name()
        );
    }

    public static UploadFileStepEntity toEntity(final UploadFileStep step) {
        return new UploadFileStepEntity(
                step.getData().getIdentifier(),
                step.getData().getTitle(),
                step.getData().getDescription(),
                step.getData().getType().name(),
                toEntity(step.getFiles())
        );
    }

    public static TheoricalTestStepEntity toEntity(final TheoricalTestStep step) {
        return new TheoricalTestStepEntity(
                step.getData().getIdentifier(),
                step.getData().getTitle(),
                step.getData().getDescription(),
                step.getData().getType().name(),
                toEntity(step.getQuestions())
        );
    }

    private static Set<FileVoEntity> toEntity(final Set<FileVo> files) {
        return files.stream().map(it -> new FileVoEntity(it.getDescription(), it.getType().name())).collect(Collectors.toSet());
    }

    private static List<String> toEntity(final List<Question> questions) {
        return questions.stream().map(Question::getIdentifier).collect(Collectors.toList());
    }

}
