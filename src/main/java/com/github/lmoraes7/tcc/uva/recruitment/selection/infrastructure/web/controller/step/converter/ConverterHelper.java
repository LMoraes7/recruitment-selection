package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeFile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeQuestion;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.*;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.request.*;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.response.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public final class ConverterHelper {


    public static ConsultResponsesFromAnExecutedStepDto toDto(final String stepIdentifier, final String type, final String applicationIdentifier) {
        return new ConsultResponsesFromAnExecutedStepDto(
                stepIdentifier,
                applicationIdentifier,
                TypeStep.valueOf(type)
        );
    }

    public static ResponsesFromAnExecutedStepResponse toResponse(final ResponsesFromAnExecutedStep result) {
        return new ResponsesFromAnExecutedStepResponse(
                result.getCandidacyIdentifier(),
                result.getStepIdentifier(),
                result.getTypeStep().name(),
                toDto(result.getTheoricalStepExecuted()),
                toDto(result.getUploadStepExecuted())
        );
    }

    private static ResponsesFromAnExecutedTheoricalStepResponse toDto(final ResponsesFromAnExecutedTheoricalStep theoricalStepExecuted) {
        if (theoricalStepExecuted == null)
            return null;

        return new ResponsesFromAnExecutedTheoricalStepResponse(
                theoricalStepExecuted.getQuestionsExecuteds().stream().map(
                        it -> new ResponsesFromAnExecutedTheoricalQuestionStepReponse(
                                it.getQuestionIdentifier(),
                                it.getAnswerIdentifier(),
                                it.getTypeQuestion().name(),
                                it.getQuestionDescription(),
                                it.getAnswerDescription(),
                                it.getDiscursiveAnswer(),
                                it.getAnswerCorrect()
                        )
                ).collect(Collectors.toList())
        );
    }

    private static ResponsesFromAnExecutedUploadStepResponse toDto(final ResponsesFromAnExecutedUploadStep uploadStepExecuted) {
        if (uploadStepExecuted == null)
            return null;

        return new ResponsesFromAnExecutedUploadStepResponse(
                uploadStepExecuted
                        .getFiles()
                        .stream()
                        .map(it -> new ResponsesFromAnExecutedUploadFileStepResponse(
                                it.getBytes(),
                                it.getTypeFile().name()
                        )).collect(Collectors.toList())
        );
    }


    public static StepDto toDto(final StepTheoricalTestRequest request) {
        return new StepDto(
                request.getTitle(),
                request.getDescription(),
                TypeStep.THEORETICAL_TEST,
                request.getQuestionsIdentifiers(),
                null
        );
    }

    public static StepDto toDto(final StepUploadFilesRequest request) {
        return new StepDto(
                request.getTitle(),
                request.getDescription(),
                TypeStep.UPLOAD_FILES,
                null,
                toDto(request.getDataUploadFiles())
        );
    }

    public static StepDto toDto(final StepExternalRequest request) {
        return new StepDto(
                request.getTitle(),
                request.getDescription(),
                TypeStep.EXTERNAL,
                null,
                null
        );
    }

    private static Set<TypeUploadFileDto> toDto(Set<TypeUploadFileRequest> dataUploadFiles) {
        if (dataUploadFiles == null)
            return null;

        return dataUploadFiles.stream().map(it -> new TypeUploadFileDto(it.getDescription(), TypeFile.valueOf(it.getType()))).collect(Collectors.toSet());
    }

    public static ReleaseStepForCandidateDto toDto(final String stepIdentifier, final String candidacyIdentifier, final ReleaseStepForCandidateRequest request) {
        if (request == null)
            return new ReleaseStepForCandidateDto(
                    stepIdentifier,
                    candidacyIdentifier,
                    null,
                    null
            );

        return new ReleaseStepForCandidateDto(
                stepIdentifier,
                candidacyIdentifier,
                request.getLink(),
                request.getDateTime()
        );
    }

    public static ConsultSpecificStepCandidacyDto toDto(
            final String stepIdentifier,
            final String type,
            final String candidacyIdentifier,
            final String selectiveProcessIdentifier
    ) {
        return new ConsultSpecificStepCandidacyDto(
                stepIdentifier,
                candidacyIdentifier,
                selectiveProcessIdentifier,
                TypeStep.valueOf(type)
        );
    }

    public static SpecificExecutionStepCandidacyResponse toResponse(SpecificExecutionStepCandidacyDto result) {
        return new SpecificExecutionStepCandidacyResponse(
                result.getCandidacyIdentifier(),
                result.getCandidateIdentifier(),
                result.getSelectiveProcessIdentifier(),
                result.getStepIdentifier(),
                toDto(result.getTheoricalTestStep()),
                toDto(result.getUploadFileStep()),
                toDto(result.getExternalStep())
        );
    }

    private static SpecificExternalStepCandidacyResponse toDto(SpecificExternalStepCandidacyDto externalStep) {
        if (externalStep == null)
            return null;

        return new SpecificExternalStepCandidacyResponse(
                externalStep.getLink(),
                externalStep.getDateTime()
        );
    }

    private static SpecificUploadFileStepCandidacyResponse toDto(SpecificUploadFileStepCandidacyDto uploadFileStep) {
        if (uploadFileStep == null)
            return null;

        return new SpecificUploadFileStepCandidacyResponse(
                uploadFileStep
                        .getFiles()
                        .stream()
                        .map(it -> new FileResponse(it.getDescription(), it.getTypeFile().name()))
                        .collect(Collectors.toList())
        );
    }

    private static SpecificTheoricalTestStepCandidacyResponse toDto(SpecificTheoricalTestStepCandidacyDto theoricalTestStep) {
        if (theoricalTestStep == null)
            return null;

        return new SpecificTheoricalTestStepCandidacyResponse(
                theoricalTestStep
                        .getQuestions()
                        .stream()
                        .map(question -> {
                            if (question.getQuestionType() == TypeQuestion.DISCURSIVE)
                                return new QuestionResponse(
                                        question.getQuestionIdentifier(),
                                        question.getQuestionDescription(),
                                        question.getQuestionType().name(),
                                        null
                                );

                            return new QuestionResponse(
                                    question.getQuestionIdentifier(),
                                    question.getQuestionDescription(),
                                    question.getQuestionType().name(),
                                    question.getAnswers()
                                            .stream()
                                            .map(it -> new AnswerResponse(it.getAnswerIdentifier(), it.getAnswerDescription()))
                                            .collect(Collectors.toList())
                            );
                        }).collect(Collectors.toList())
        );
    }

    public static ExecuteStepCandidacyDto toDto(
            String stepIdentifier,
            String candidacyIdentifier,
            String selectiveProcessIdentifier,
            ExecuteStepCandidacyTheoricalTestRequest request
    ) {
        return new ExecuteStepCandidacyDto(
                stepIdentifier,
                candidacyIdentifier,
                selectiveProcessIdentifier,
                TypeStep.THEORETICAL_TEST,
                new ExecuteTheoricalTestStepCandidacyDto(
                        request.getQuestions()
                                .stream()
                                .map(it -> new ExecuteQuestionDto(
                                        it.getQuestionIdentifier(),
                                        TypeQuestion.valueOf(it.getType()),
                                        new ExecuteAnswerDto(it.getAnswer().getAnswer()))
                                ).collect(Collectors.toList())
                ),
                null
        );
    }

    public static ExecuteStepCandidacyDto toDto(
            String stepIdentifier,
            String candidacyIdentifier,
            String selectiveProcessIdentifier,
            MultipartFile[] files
    ) {
        return new ExecuteStepCandidacyDto(
                stepIdentifier,
                candidacyIdentifier,
                selectiveProcessIdentifier,
                TypeStep.UPLOAD_FILES,
                null,
                new ExecuteUploadFileStepCandidacyDto(
                        Arrays.stream(files).map(it -> {
                            try {
                                return new ExecuteFileDto(
                                        it.getOriginalFilename(),
                                        it.getBytes(),
                                        TypeFile.valueOf(FilenameUtils.getExtension(it.getOriginalFilename()))
                                );
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }).collect(Collectors.toList())
                )
        );
    }
}
