package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.consult.answer;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ConsultResponsesFromAnExecutedStepDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ResponsesFromAnExecutedStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ResponsesFromAnExecutedUploadFileStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ResponsesFromAnExecutedUploadStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.UploadFileStepCandidacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ConsultResponsesFromAnExecutedUploadFileStep implements ConsultResponsesFromAnExecutedStepStrategy {
    private final UploadFileStepCandidacyRepository uploadFileStepCandidacyRepository;

    @Autowired
    public ConsultResponsesFromAnExecutedUploadFileStep(final UploadFileStepCandidacyRepository uploadFileStepCandidacyRepository) {
        this.uploadFileStepCandidacyRepository = uploadFileStepCandidacyRepository;
    }

    @Override
    public TypeStep getType() {
        return TypeStep.UPLOAD_FILES;
    }

    @Override
    public ResponsesFromAnExecutedStep execute(final ConsultResponsesFromAnExecutedStepDto dto) {
        final List<ResponsesFromAnExecutedUploadFileStep> result = this.uploadFileStepCandidacyRepository
                .consultTestExecuted(dto.getApplicationIdentifier(), dto.getStepIdentifier());

        return new ResponsesFromAnExecutedStep(
                dto.getApplicationIdentifier(),
                dto.getStepIdentifier(),
                dto.getTypeStep(),
                null,
                new ResponsesFromAnExecutedUploadStep(result)
        );
    }

}
