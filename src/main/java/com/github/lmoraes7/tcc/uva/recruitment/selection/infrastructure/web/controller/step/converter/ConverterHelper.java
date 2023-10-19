package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.converter;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeFile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.*;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.request.ReleaseStepForCandidateRequest;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.request.StepRequest;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.request.TypeUploadFileRequest;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.step.response.*;

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
                uploadStepExecuted.getFiles().stream().map(it -> new ResponsesFromAnExecutedUploadFileStepResponse(it.getBytes(), it.getTypeFile().name())).collect(Collectors.toList())
        );
    }


    public static StepDto toDto(final StepRequest request) {
        return new StepDto(
                request.getTitle(),
                request.getDescription(),
                TypeStep.valueOf(request.getType()),
                request.getQuestionsIdentifiers(),
                toDto(request.getDataUploadFiles())
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

    public static void toResponse(SpecificExecutionStepCandidacyDto result) {

    }

}
