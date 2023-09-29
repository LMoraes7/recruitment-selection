package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.InternalErrorException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.ExternalStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Question;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.TheoricalTestStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.UploadFileStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.FileVo;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.*;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.entity.ExternalStepEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.entity.TheoricalTestStepEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.entity.UploadFileStepEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.entity.vo.FileVoEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.vo.ExternalStepCandidacyVo;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.vo.StepFindVo;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.vo.TheoricalTestStepCandidacyVo;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.rowmapper.vo.UploadFileStepCandidacyVo;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.INTG_002;

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

    public static SpecificExecutionStepCandidacyDto theoricalTesttoDto(final List<TheoricalTestStepCandidacyVo> theoricalTestStepCandidacyVos) {
        final Map<String, List<TheoricalTestStepCandidacyVo>> cacheQuestions = new HashMap<>();
        final Map<String, List<TheoricalTestStepCandidacyVo>> mapQuestions = new HashMap<>();

        theoricalTestStepCandidacyVos.forEach(it -> {
            final List<TheoricalTestStepCandidacyVo> value = cacheQuestions.get(it.getQuestionIdentifier());

            if (value != null)
                mapQuestions.put(it.getQuestionIdentifier(), value);
            else {
                final List<TheoricalTestStepCandidacyVo> result = theoricalTestStepCandidacyVos
                        .stream()
                        .filter(x -> Objects.equals(x.getQuestionIdentifier(), it.getQuestionIdentifier()))
                        .toList();
                cacheQuestions.put(it.getQuestionIdentifier(), result);

                mapQuestions.put(it.getQuestionIdentifier(), result);
            }
        });

        final List<QuestionDto> questions = new ArrayList<>();
        mapQuestions.forEach((key, value) -> {
            final List<AnswerDto> answers = value
                    .stream()
                    .map(it -> new AnswerDto(it.getAnswerIdentifier(), it.getAnswerDescription()))
                    .toList();

            final TheoricalTestStepCandidacyVo first = value.stream()
                    .findFirst()
                    .orElseThrow(() -> new InternalErrorException(INTG_002));

            questions.add(new QuestionDto(first.getQuestionIdentifier(), first.getQuestionDescription(), first.getQuestionType(), answers));
        });

        final TheoricalTestStepCandidacyVo first = theoricalTestStepCandidacyVos.stream()
                .findFirst()
                .orElseThrow(() -> new InternalErrorException(INTG_002));

        return new SpecificExecutionStepCandidacyDto(
                first.getCandidacyIdentifier(),
                first.getCandidateIdentifier(),
                first.getSelectiveProcessIdentifier(),
                first.getStepIdentifier(),
                new SpecificTheoricalTestStepCandidacyDto(questions),
                null,
                null
        );
    }

    public static SpecificExecutionStepCandidacyDto uploadFileVotoDto(final List<UploadFileStepCandidacyVo> uploadFileStepCandidacyVos) {
        final List<FileDto> files = uploadFileStepCandidacyVos
                .stream()
                .map(it -> new FileDto(it.getFileDescription(), it.getFileType()))
                .toList();

        final UploadFileStepCandidacyVo first = uploadFileStepCandidacyVos.stream()
                .findFirst()
                .orElseThrow(() -> new InternalErrorException(INTG_002));

        return new SpecificExecutionStepCandidacyDto(
                first.getCandidacyIdentifier(),
                first.getCandidateIdentifier(),
                first.getSelectiveProcessIdentifier(),
                first.getStepIdentifier(),
                null,
                new SpecificUploadFileStepCandidacyDto(files),
                null
        );
    }

    public static SpecificExecutionStepCandidacyDto externalVotoDto(final ExternalStepCandidacyVo externalStepCandidacyVo) {
        return new SpecificExecutionStepCandidacyDto(
                externalStepCandidacyVo.getCandidacyIdentifier(),
                externalStepCandidacyVo.getCandidateIdentifier(),
                externalStepCandidacyVo.getSelectiveProcessIdentifier(),
                externalStepCandidacyVo.getStepIdentifier(),
                null,
                null,
                new SpecificExternalStepCandidacyDto(
                        externalStepCandidacyVo.getExternalLink(),
                        externalStepCandidacyVo.getExternalDateTime()
                )
        );
    }

    public static ExecuteStepCandidacyFindDto toDto(final StepFindVo stepFindVo) {
        LocalDate releaseDate = null;
        LocalDate limitTime = null;

        if (stepFindVo.getReleaseDate() != null) {
            releaseDate = stepFindVo.getReleaseDate();

            if (stepFindVo.getLimitTime() != null && stepFindVo.getLimitTime() != 0L)
                limitTime = releaseDate.plusDays(stepFindVo.getLimitTime());
        }

        return new ExecuteStepCandidacyFindDto(
                stepFindVo.getIdentifier(),
                stepFindVo.getStatus(),
                limitTime,
                releaseDate
        );
    }

}
