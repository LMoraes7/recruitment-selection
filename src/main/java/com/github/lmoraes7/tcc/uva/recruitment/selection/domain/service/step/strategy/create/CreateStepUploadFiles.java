package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.create;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Step;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.StepDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.UploadFilelStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.converter.ConverterHelper.toUploadFileStepModel;

@Service
public final class CreateStepUploadFiles implements CreateStepStrategy {
    private final UploadFilelStepRepository uploadFilelStepRepository;

    @Autowired
    public CreateStepUploadFiles(final UploadFilelStepRepository uploadFilelStepRepository) {
        this.uploadFilelStepRepository = uploadFilelStepRepository;
    }

    @Override
    public TypeStep getTypeStep() {
        return TypeStep.UPLOAD_FILES;
    }

    @Override
    public Step execute(final StepDto dto) {
        return this.uploadFilelStepRepository.save(toUploadFileStepModel(dto));
    }

}
