package com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.strategy.execute;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeFile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.TypeStep;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ExecuteFileDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ExecuteStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.service.step.dto.ExecuteUploadFileStepCandidacyDto;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.UploadFileStepCandidacyRepository;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.step.UploadFilelStepRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_016;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.error.Error.APIX_017;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class ExecuteUploadFiletStepCandidacyTest {
    private final UploadFilelStepRepository uploadFilelStepRepository = mock(UploadFilelStepRepository.class);
    private final UploadFileStepCandidacyRepository uploadFileStepCandidacyRepository = mock(UploadFileStepCandidacyRepository.class);
    private final ExecuteUploadFiletStepCandidacy executeUploadFiletStepCandidacy = new ExecuteUploadFiletStepCandidacy(this.uploadFilelStepRepository, this.uploadFileStepCandidacyRepository);

    private String candidateIdentifier;
    private ExecuteStepCandidacyDto executeStepCandidacyDto;

    @BeforeEach
    void setUp() {
        this.candidateIdentifier = GeneratorIdentifier.forCandidate();
        this.executeStepCandidacyDto = new ExecuteStepCandidacyDto(
                GeneratorIdentifier.forStep(),
                GeneratorIdentifier.forCandidacy(),
                GeneratorIdentifier.forSelectiveProcess(),
                TypeStep.UPLOAD_FILES,
                null,
                new ExecuteUploadFileStepCandidacyDto(
                        List.of(
                                new ExecuteFileDto(
                                        UUID.randomUUID().toString(),
                                        new byte[] {1, 2, 3, 5},
                                        TypeFile.MP4
                                )
                        )
                )
        );
    }

    @Test
    void when_prompted_you_must_save_the_successfully_executed_step() {
        when(this.uploadFilelStepRepository.findTypeFiles(this.executeStepCandidacyDto.getStepIdentifier()))
                .thenReturn(List.of(TypeFile.MP4));

        assertDoesNotThrow(() -> this.executeUploadFiletStepCandidacy.execute(this.candidateIdentifier, this.executeStepCandidacyDto));

        verify(this.uploadFilelStepRepository, only()).findTypeFiles(this.executeStepCandidacyDto.getStepIdentifier());
        verify(this.uploadFileStepCandidacyRepository, only()).saveTestExecuted(
                this.executeStepCandidacyDto.getCandidacyIdentifier(),
                this.executeStepCandidacyDto.getStepIdentifier(),
                this.executeStepCandidacyDto.getUploadFile()
        );
    }

    @Test
    void when_requested_it_must_throw_a_BusinessException_when_saving_a_number_of_files_that_is_not_compatible_with_the_desired_amount() {
        when(this.uploadFilelStepRepository.findTypeFiles(this.executeStepCandidacyDto.getStepIdentifier()))
                .thenReturn(List.of(TypeFile.PDF, TypeFile.MP4, TypeFile.PDF));

        final BusinessException exception = assertThrows(
                BusinessException.class,
                () -> this.executeUploadFiletStepCandidacy.execute(this.candidateIdentifier, this.executeStepCandidacyDto)
        );

        assertEquals(APIX_016, exception.getError());

        verify(this.uploadFilelStepRepository, only()).findTypeFiles(this.executeStepCandidacyDto.getStepIdentifier());
        verifyNoInteractions(this.uploadFileStepCandidacyRepository);
    }

    @Test
    void when_prompted_should_throw_a_BusinessException_when_saving_a_file_of_an_undesired_type() {
        when(this.uploadFilelStepRepository.findTypeFiles(this.executeStepCandidacyDto.getStepIdentifier()))
                .thenReturn(List.of(TypeFile.PDF));

        final BusinessException exception = assertThrows(
                BusinessException.class,
                () -> this.executeUploadFiletStepCandidacy.execute(this.candidateIdentifier, this.executeStepCandidacyDto)
        );

        assertEquals(APIX_017, exception.getError());

        verify(this.uploadFilelStepRepository, only()).findTypeFiles(this.executeStepCandidacyDto.getStepIdentifier());
        verifyNoInteractions(this.uploadFileStepCandidacyRepository);
    }

}