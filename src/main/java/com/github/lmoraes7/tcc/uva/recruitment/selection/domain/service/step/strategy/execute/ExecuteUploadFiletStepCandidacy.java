package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.execute;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeFile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ExecuteFileDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ExecuteStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.UploadFileStepCandidacyRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.UploadFilelStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_016;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_017;

@Component
public final class ExecuteUploadFiletStepCandidacy implements ExecuteStepCandidacyStrategy {
    private final UploadFilelStepRepository uploadFilelStepRepository;
    private final UploadFileStepCandidacyRepository uploadFileStepCandidacyRepository;

    @Autowired
    public ExecuteUploadFiletStepCandidacy(
            final UploadFilelStepRepository uploadFilelStepRepository,
            final UploadFileStepCandidacyRepository uploadFileStepCandidacyRepository
    ) {
        this.uploadFilelStepRepository = uploadFilelStepRepository;
        this.uploadFileStepCandidacyRepository = uploadFileStepCandidacyRepository;
    }

    @Override
    public TypeStep getTypeStep() {
        return TypeStep.UPLOAD_FILES;
    }

    @Override
    public void execute(final String candidateIdentifier, final ExecuteStepCandidacyDto dto) {
        final List<TypeFile> typeFiles = this.uploadFilelStepRepository.findTypeFiles(dto.getStepIdentifier());

        if (dto.getUploadFile().getFiles().size() != typeFiles.size())
            throw new BusinessException(APIX_016, "File upload step had more or fewer files sent than defined in the step");

        for (final ExecuteFileDto file : dto.getUploadFile().getFiles()) {
            if (!typeFiles.contains(file.getType()))
                throw new BusinessException(APIX_017, "File upload step had invalid file types");
        }

        this.uploadFileStepCandidacyRepository.saveTestExecuted(
                dto.getCandidacyIdentifier(),
                dto.getStepIdentifier(),
                dto.getUploadFile()
        );
    }

}
