package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.consult;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.StepCandidacy;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ConsultSpecificStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.SpecificExecutionStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.TheoricalTestStepCandidacyRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.UploadFileStepCandidacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class ConsultUploadFileExecutionStepCandidacy implements ConsultSpecificExecutionStepCandidacyStrategy {
    private final UploadFileStepCandidacyRepository uploadFileStepCandidacyRepository;

    @Autowired
    public ConsultUploadFileExecutionStepCandidacy(final UploadFileStepCandidacyRepository uploadFileStepCandidacyRepository) {
        this.uploadFileStepCandidacyRepository = uploadFileStepCandidacyRepository;
    }

    @Override
    public TypeStep getTypeStep() {
        return TypeStep.UPLOAD_FILES;
    }

    @Override
    public SpecificExecutionStepCandidacyDto execute(final String candidateIdentifier, final ConsultSpecificStepCandidacyDto dto) {
        return this.uploadFileStepCandidacyRepository.findFileToBeSent(
                dto.getCandidacyIdentifier(),
                candidateIdentifier,
                dto.getSelectiveProcessIdentifier(),
                dto.getStepIdentifier()
        ).orElseThrow(() -> new NotFoundException(dto.getStepIdentifier(), StepCandidacy.class));
    }

}
